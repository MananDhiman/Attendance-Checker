package com.manandhiman.attendancechecker.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
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
import com.manandhiman.attendancechecker.viewmodel.AttendanceHistoryViewModel

@Composable
fun AttendanceHistoryScreen(viewModel: AttendanceHistoryViewModel) {

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
//          if(it == "") historyList.value = viewModel.historyAttendance
        },
        label = { Text(text = "Search using Subject Name or Date") }
      )
    }

    LazyColumn {
      items(viewModel.historyList.size) {
        AttendanceHistoryItem(viewModel.historyList[it])
        Divider()
      }
    }
  }
}

@Composable
fun AttendanceHistoryItem(attendance: Attendance) {
  Column (
    Modifier
      .fillMaxWidth(1f)
      .wrapContentHeight()
      .padding(16.dp)
  ) {
    Text(text = "${attendance.date} ${attendance.subjectName} ${attendance.status}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    Text(text = "${attendance.presentDays} / ${attendance.totalDays}")
  }
}