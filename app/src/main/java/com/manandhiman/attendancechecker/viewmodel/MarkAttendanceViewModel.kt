package com.manandhiman.attendancechecker.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.manandhiman.attendancechecker.data.AppDatabase
import com.manandhiman.attendancechecker.model.Attendance
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MarkAttendanceViewModel(application: Application): AndroidViewModel(application) {

  private val sdf = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
  private val currentDate = sdf.format(Date())

  private val db = Room.databaseBuilder(
    getApplication(),
    AppDatabase::class.java, "attendance-record",
  ).allowMainThreadQueries().build()

  private val subjectDao = db.subjectDao()
  private val attendanceDao = db.attendanceDao()

  val latestAttendanceBySubject = mutableStateOf(attendanceDao.getLastBySubject())

  fun markPresent(subjectName: String) {
    val prevAtt = attendanceDao.getLastById(subjectName)

    val att = Attendance(
      prevAtt.subjectName,
      currentDate,
      "Present",
      prevAtt.totalDays + 1,
      prevAtt.presentDays + 1
    )
    attendanceDao.insert(att)
    latestAttendanceBySubject.value = attendanceDao.getLastBySubject()
  }

  fun markAbsent(subjectName: String) {
    val prevAtt = attendanceDao.getLastById(subjectName)

    val att = Attendance(
      prevAtt.subjectName,
      currentDate,
      "Absent",
      prevAtt.totalDays + 1,
      prevAtt.presentDays
    )
    attendanceDao.insert(att)
    latestAttendanceBySubject.value = attendanceDao.getLastBySubject()
  }

  fun formattedCurrentAttendance(presentDays: Int, totalDays: Int): String {
    val percentage = ((presentDays.toDouble() / totalDays.toDouble()) * 100)
    val df = DecimalFormat("##.##")
    df.roundingMode = RoundingMode.FLOOR

    val formattedPercentage =  df.format(percentage)
    return "$presentDays/$totalDays = $formattedPercentage%"
  }
}