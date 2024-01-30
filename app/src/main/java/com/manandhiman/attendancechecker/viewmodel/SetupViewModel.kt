package com.manandhiman.attendancechecker.viewmodel

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.manandhiman.attendancechecker.MainActivity
import com.manandhiman.attendancechecker.data.AppDatabase
import com.manandhiman.attendancechecker.model.Attendance
import com.manandhiman.attendancechecker.model.Subject

class SetupViewModel(private val application: Application) : AndroidViewModel(application) {

  private val db = Room.databaseBuilder(
    getApplication(),
    AppDatabase::class.java, "attendance-record",
  ).allowMainThreadQueries().build()

  private val subjectDao = db.subjectDao()
  private val attendanceDao = db.attendanceDao()

  var confirmedNumberSubjects = mutableIntStateOf(3)

  val subjectList: List<Subject> = subjectDao.getAllSubjects()

  fun isSetup() = subjectList.isNotEmpty()

  val subjectNames = mutableStateListOf<String>()

  fun addSubjectsToDB() {
    val subjectList: MutableList<Subject> = mutableListOf()

    for(i in 0..<confirmedNumberSubjects.intValue) subjectList.add(Subject(subjectNames[i].trim()))

    subjectDao.addSubjects(subjectList)
    for (i in subjectList) {
      val att = Attendance(
        subjectName = i.name, date = "", status = "", totalDays = 0, presentDays = 0)
      attendanceDao.insert(att)
    }

    restartApp()
  }

  private fun restartApp() {
    application.startActivity(Intent(application, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
  }

  fun clearAllDataFromDB() {
    subjectDao.deleteAll()
    attendanceDao.deleteAll()
    restartApp()
  }

}