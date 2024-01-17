package com.manandhiman.attendancechecker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manandhiman.attendancechecker.model.Attendance
import com.manandhiman.attendancechecker.viewmodel.MainViewModel

@Composable
fun MarkAttendanceScreen(viewModel: MainViewModel) {

  LazyColumn(
    Modifier
      .fillMaxWidth(1f)
      .wrapContentHeight()
      .padding(8.dp)
  ) {
    items(viewModel.latestAttendanceBySubject.value.size) {
      MarkAttendanceItem(viewModel, viewModel.latestAttendanceBySubject.value[it])
    }
  }
}

@Composable
fun MarkAttendanceItem(
  viewModel: MainViewModel,
  attendance: Attendance,
) {
  Column (
    Modifier
      .fillMaxWidth(1f)
      .wrapContentHeight()
  ) {

    Text(text = attendance.subjectName, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    Text(text = "${attendance.presentDays} / ${attendance.totalDays} = " +
        viewModel.formattedCurrentAttendance(attendance.presentDays, attendance.totalDays)
    )

    Row (Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceEvenly) {
      Button(
        onClick = { viewModel.markPresent(attendance.subjectName) },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Green, contentColor = Color.Black)
      ) {
        Text(text = "Present")
      }
      Button(
        onClick = { viewModel.markAbsent(attendance.subjectName) },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.Black)
      ) {
        Text(text = "Absent")

      }
    }
    Divider()
  }
}