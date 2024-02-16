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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.manandhiman.attendancechecker.ui.AttendanceHistoryScreen
import com.manandhiman.attendancechecker.ui.MarkAttendanceScreen
import com.manandhiman.attendancechecker.ui.Screens
import com.manandhiman.attendancechecker.ui.SetupScreen
import com.manandhiman.attendancechecker.ui.theme.AttendanceCheckerTheme
import com.manandhiman.attendancechecker.viewmodel.MainViewModel
import com.manandhiman.attendancechecker.viewmodel.SetupViewModel

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      AttendanceCheckerTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          
          val setupViewModel = ViewModelProvider(this@MainActivity)[SetupViewModel::class.java]
          val mainViewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
          
          UI(setupViewModel, mainViewModel)

        }
      }
    }
  }

  @Composable
  fun UI(setupViewModel: SetupViewModel, mainViewModel: MainViewModel) {
    val currentScreen = rememberSaveable {
      mutableStateOf(Screens.MarkAttendanceScreen)
    }
    Column {

      if(!setupViewModel.isSetup()) {
        SetupScreen(setupViewModel)
        return
      }

      TopNavigationBar(currentScreen)

      when(currentScreen.value) {
        Screens.MarkAttendanceScreen -> MarkAttendanceScreen(viewModel = mainViewModel)
        Screens.AttendanceHistoryScreen -> AttendanceHistoryScreen(viewModel = mainViewModel)
        Screens.SetupScreen -> SetupScreen(viewModel = setupViewModel)
      }
    }
  }

  @Composable
  fun TopNavigationBar(currentScreen: MutableState<Screens>) {
    Spacer(modifier = Modifier.height(8.dp))
    Column (Modifier.fillMaxWidth(1f)) {
      Row (
        Modifier
          .fillMaxWidth()
          .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
      ) {
        Button(onClick = { currentScreen.value = Screens.MarkAttendanceScreen }, modifier = Modifier.weight(1f)) {
          Text(text = "Mark New", maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Button(onClick = { currentScreen.value = Screens.AttendanceHistoryScreen }, modifier = Modifier.weight(1f)) {
          Text(text = "History", maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Button(onClick = { currentScreen.value = Screens.SetupScreen }, modifier = Modifier.weight(1f)) {
          Text(text = "Setup", maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
      }
    }
  }

  @Preview(showBackground = true)
  @Composable
  fun TopNavigationBarPreview() {
    TopNavigationBar(currentScreen = mutableStateOf(Screens.MarkAttendanceScreen))
  }
}

