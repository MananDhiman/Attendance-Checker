package com.manandhiman.attendancechecker.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.manandhiman.attendancechecker.data.AppDatabase
import com.manandhiman.attendancechecker.model.Attendance
import com.manandhiman.attendancechecker.model.Subject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SetupViewModel(application: Application) : AndroidViewModel(application) {

  private val db = Room.databaseBuilder(
    getApplication(),
    AppDatabase::class.java, "attendance-record",
  ).allowMainThreadQueries().build()

  private val subjectDao = db.subjectDao()
  private val attendanceDao = db.attendanceDao()

  var subjectsCount = mutableIntStateOf(3)

  val subjectNames = mutableStateListOf<String>()

  fun updateSubjectsCount(input: String) {
    if(input != "" || input.contains('-') || input.contains('.')) return
    subjectsCount.intValue = input.toInt()
  }

  fun isSetup() = subjectDao.getAllSubjects().isNotEmpty()

  fun addSubjectsToDB() {
    val subjectList: MutableList<Subject> = mutableListOf()

    for(i in 0..<subjectsCount.intValue) subjectList.add(Subject(subjectNames[i]))

    subjectDao.addSubjects(subjectList)
    for (i in subjectList) {
      val att = Attendance(i.name, "", "", 0, 0)
      attendanceDao.insert(att)
    }
  }

}