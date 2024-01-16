package com.manandhiman.attendancechecker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.manandhiman.attendancechecker.ui.AttendanceHistoryScreen
import com.manandhiman.attendancechecker.ui.MarkAttendanceScreen
import com.manandhiman.attendancechecker.ui.SetupScreen
import com.manandhiman.attendancechecker.ui.theme.AttendanceCheckerTheme
import com.manandhiman.attendancechecker.viewmodel.AttendanceHistoryViewModel
import com.manandhiman.attendancechecker.viewmodel.MarkAttendanceViewModel
import com.manandhiman.attendancechecker.viewmodel.SetupViewModel

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val markAttendanceViewModel = ViewModelProvider(this)[MarkAttendanceViewModel::class.java]
    val setupViewModel = ViewModelProvider(this)[SetupViewModel::class.java]
    val attendanceHistoryViewModel = ViewModelProvider(this)[AttendanceHistoryViewModel::class.java]

    setContent {
      AttendanceCheckerTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

          val showHistoryScreen = remember {
            mutableStateOf(false)
          }

          Column {
            if(!setupViewModel.isSetup()) {
              SetupScreen(setupViewModel)
              return@Surface
            }

            TopNavigationBar(showHistoryScreen)

            if(showHistoryScreen.value) AttendanceHistoryScreen(attendanceHistoryViewModel)
            else MarkAttendanceScreen(markAttendanceViewModel)

          }
        }
      }
    }
  }

  @Composable
  fun TopNavigationBar(showHistoryScreen: MutableState<Boolean>) {
    Spacer(modifier = Modifier.height(8.dp))
    Column (Modifier.fillMaxWidth(1f)) {
      Row (
        Modifier.fillMaxWidth(1f),
        horizontalArrangement = Arrangement.SpaceEvenly
      ) {
        Button(onClick = { showHistoryScreen.value = false }) {
          Text(text = "New Attendance")
        }
        Button(onClick = { showHistoryScreen.value = true }) {
          Text(text = "Attendance History")
        }
      }
    }
  }
}

