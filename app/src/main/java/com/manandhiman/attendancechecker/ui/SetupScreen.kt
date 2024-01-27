package com.manandhiman.attendancechecker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.manandhiman.attendancechecker.viewmodel.SetupViewModel

import androidx.compose.material3.AlertDialog

import androidx.compose.material3.ButtonDefaults

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.tooling.preview.Preview

import com.manandhiman.attendancechecker.R


@Composable
fun SetupScreen(viewModel: SetupViewModel) {
  SetupScreen(
    viewModel.isSetup(),
    viewModel.confirmedNumberSubjects,
    viewModel.subjectNames,
    viewModel::addSubjectsToDB,
    viewModel::clearAllData
  )
}

@Composable
fun SetupScreen(
  isSetup: Boolean,
  confirmedNumberSubjects: MutableIntState,
  subjectNames: SnapshotStateList<String>,
  addSubjectsToDB: () -> Unit,
  clearAllData: () -> Unit
) {

    if(isSetup) ClearAllDataUI(clearAllData)
    else SubjectsNumberAndNameSection(confirmedNumberSubjects,subjectNames, addSubjectsToDB)

}

@Composable
fun SubjectsNumberAndNameSection(
  confirmedNumberSubjects: MutableIntState,
  subjectNames: SnapshotStateList<String>,
  addSubjectsToDB: () -> Unit
) {

  Spacer(modifier = Modifier.height(8.dp))

  Row (
    Modifier
      .fillMaxWidth(1f)
      .wrapContentHeight()
      .padding(32.dp),
    horizontalArrangement = Arrangement.SpaceEvenly,
    verticalAlignment = Alignment.CenterVertically
  ) {

    val numberOfSubjects = remember {
      mutableStateOf("3")
    }

    OutlinedTextField (
      value = numberOfSubjects.value,
      onValueChange = { numberOfSubjects.value = it },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
      label = { Text(text = "Enter Number of Subjects") },
      modifier = Modifier.fillMaxWidth(0.75f),
      maxLines = 1
    )

    Spacer(modifier = Modifier.width(8.dp))

    Button (
      onClick = { confirmedNumberSubjects.intValue = numberOfSubjects.value.toInt() },
      modifier = Modifier.fillMaxWidth(1f)
    ) {
      Text(text = "Confirm", maxLines = 1)
    }
  }

  Spacer(modifier = Modifier.height(16.dp))

  Column (
    Modifier
      .fillMaxWidth(1f)
      .padding(bottom = 128.dp)
  ) {

    LazyColumn (
      Modifier
        .fillMaxWidth(0.75f)
        .align(Alignment.CenterHorizontally),
      verticalArrangement = Arrangement.Center
    ) {
      items(confirmedNumberSubjects.intValue) { ind ->
        subjectNames.add("")

        OutlinedTextField(
          modifier = Modifier.fillMaxSize(1f),
          value = subjectNames[ind],
          onValueChange = { subjectNames[ind] = it },
          placeholder = { Text("Subject ${ind+1} Name") },
          maxLines = 1
        )
        Spacer(modifier = Modifier.height(4.dp))

//        Row (
//          Modifier
//            .fillMaxWidth(1f)
//            .padding(16.dp)
//        ) {
//          TextField(value = "A", onValueChange = {}, modifier = Modifier.fillMaxWidth(0.5f))
//          Spacer(modifier = Modifier.width(4.dp))
//          TextField(value = "B", onValueChange = {}, modifier = Modifier.fillMaxWidth(1f))
//        }


        Spacer(modifier = Modifier.height(8.dp))

      }
    }
    Button(
      onClick = { if(confirmedNumberSubjects.intValue != 0) addSubjectsToDB() },
    Modifier.align(Alignment.CenterHorizontally)
    ) {
      Text(text = "Save Subjects")
    }

  }

}

@Composable
fun ClearAllDataUI(clearAllData: () -> Unit) {

  val alertDialogVisible = remember {
    mutableStateOf(false)
  }

  Row (
    Modifier
      .fillMaxWidth(1f)
      .wrapContentHeight()
      .padding(8.dp),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Button(
      onClick = { alertDialogVisible.value = true },
      colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.Black)
    ) {
      Text(text = "Clear All Data and Reset App")
    }
  }

  if(alertDialogVisible.value) ClearDataAlertDialog(alertDialogVisible, clearAllData)
}

@Composable
fun ClearDataAlertDialog(alertDialogVisible: MutableState<Boolean>, clearAllData: () -> Unit) {
  AlertDialog(
    onDismissRequest = { alertDialogVisible.value = false },
    title = { Text(text = "Reset App Data?") },
    text = { Text(stringResource(id = R.string.clear_all_data_text)) },

    confirmButton = {
      Button(
        onClick = {
          clearAllData()
          alertDialogVisible.value = false
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.Black)
      ) { Text("Yes, Delete") }
    },
    dismissButton = {
      Button(
        onClick = { alertDialogVisible.value = false },
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray, contentColor = Color.Black)
      ) { Text("No, Cancel") }
    }
  )
}

@Preview(showBackground = true)
@Composable
fun SetupScreenPreview() {
  Column {
    SetupScreen(true, confirmedNumberSubjects = mutableIntStateOf(3), subjectNames = SnapshotStateList(), addSubjectsToDB = {}, clearAllData = {})
    SetupScreen(false, confirmedNumberSubjects = mutableIntStateOf(3), subjectNames = SnapshotStateList(), addSubjectsToDB = {}, clearAllData = {})
  }


//  AlertDialogSample(mutableStateOf(true), {})
}