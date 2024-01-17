package com.manandhiman.attendancechecker.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manandhiman.attendancechecker.model.Attendance
import com.manandhiman.attendancechecker.viewmodel.MainViewModel

@Composable
fun AttendanceHistoryScreen(viewModel: MainViewModel) {

  Column {

    val inputSearchQuery = remember {
      mutableStateOf("")
    }

    Row (
      Modifier
        .fillMaxWidth(1f)
        .wrapContentHeight()
        .padding(16.dp)
    ) {

      OutlinedTextField (
        value = inputSearchQuery.value,
        onValueChange = {
          inputSearchQuery.value = it
          if(it == "") viewModel.reloadFromDb()
          else viewModel.searchHistory(it)
        },
        label = { Text(text = "Search using Subject Name or Date") },
        modifier = Modifier.fillMaxWidth(1f)
      )
    }

    LazyColumn {
      items(viewModel.historyAttendance.value.size) {
        AttendanceHistoryItem(
          viewModel.historyAttendance.value[it],
          viewModel::formattedCurrentAttendance,
          viewModel::deleteFromDB
        )
        Divider()
      }
    }
  }
}

@Composable
fun AttendanceHistoryItem(
  attendance: Attendance,
  formattedCurrentAttendance: (Int, Int) -> String,
  deleteAttendance: (Attendance) -> Unit
) {

  val dropDownExpanded = remember {
    mutableStateOf(false)
  }

  Column (
    Modifier
      .fillMaxWidth(1f)
      .wrapContentHeight()
      .padding(16.dp)
      .clickable { dropDownExpanded.value = !dropDownExpanded.value }
  ) {
    Text(text = "${attendance.date} ${attendance.subjectName} ${attendance.status}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    Text(text = "${attendance.presentDays} / ${attendance.totalDays} = " +
        formattedCurrentAttendance(attendance.presentDays, attendance.totalDays))

    DropdownMenu(
      expanded = dropDownExpanded.value,
      onDismissRequest = { dropDownExpanded.value = false }
    ) {
      DropdownMenuItem(
        text = {  Text("Delete") },
        onClick = { deleteAttendance(attendance) }
      )

    }
  }
}
