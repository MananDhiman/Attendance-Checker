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

@Composable
fun SetupScreen(viewModel: SetupViewModel) {

  val numberOfSubjects = remember {
    mutableStateOf("3")
  }

  Column ( Modifier.fillMaxSize(1f) ) {

    Spacer(modifier = Modifier.height(8.dp))

    Row (
      Modifier
        .fillMaxWidth(1f)
        .wrapContentHeight()
        .padding(8.dp),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically
    ) {

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
        onClick = { viewModel.subjectsCount.intValue = numberOfSubjects.value.toInt() },
        modifier = Modifier.fillMaxWidth(1f)
      ) {
        Text(text = "Confirm", maxLines = 1)
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    LazyColumn (
      Modifier.align(Alignment.CenterHorizontally)
    ) {
      items(viewModel.subjectsCount.intValue) { ind ->
        viewModel.subjectNames.add("")

        OutlinedTextField(
          value = viewModel.subjectNames[ind],
          onValueChange = { viewModel.subjectNames[ind] = it.trim() },
          placeholder = { Text("Subject Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))

      }
    }
    Button(
      onClick = {
        if(viewModel.subjectsCount.intValue != 0) viewModel.addSubjectsToDB()
      },
      Modifier.align(Alignment.CenterHorizontally)
    ) {
      Text(text = "Save Subjects")
    }
  }
}