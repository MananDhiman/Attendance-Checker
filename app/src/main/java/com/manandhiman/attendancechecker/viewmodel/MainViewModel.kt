package com.manandhiman.attendancechecker.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.manandhiman.attendancechecker.data.AppDatabase
import com.manandhiman.attendancechecker.model.Attendance
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel(application: Application): AndroidViewModel(application) {

  private val sdf = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
  private val currentDate = sdf.format(Date())

  private val db = Room.databaseBuilder(
    getApplication(),
    AppDatabase::class.java, "attendance-record",
  ).allowMainThreadQueries().build()

  private val attendanceDao = db.attendanceDao()

  private val _latestAttendanceBySubject: MutableState<List<Attendance>> = mutableStateOf(attendanceDao.getLastBySubject())
  val latestAttendanceBySubject get() = _latestAttendanceBySubject.value

  private val _historyAttendance: MutableState<List<Attendance>> = mutableStateOf(attendanceDao.getAll().filter { it.totalDays != 0 })
  val historyAttendance get() = _historyAttendance.value

  val inputSearchQuery = mutableStateOf("")

  private fun reloadFromDb() {
    _latestAttendanceBySubject.value = attendanceDao.getLastBySubject()
    _historyAttendance.value = attendanceDao.getAll().filter { it.totalDays != 0 }
  }

  fun markPresent(subjectName: String) {
    val prevAtt = attendanceDao.getLastById(subjectName)

    val att = Attendance(
      subjectName = prevAtt.subjectName,
      date = currentDate,
      status = "Present",
      totalDays = prevAtt.totalDays + 1,
      presentDays = prevAtt.presentDays + 1
    )
    attendanceDao.insert(att)
    reloadFromDb()
  }

  fun markAbsent(subjectName: String) {
    val prevAtt = attendanceDao.getLastById(subjectName)

    val att = Attendance(
      subjectName = prevAtt.subjectName,
      date = currentDate,
      status = "Present",
      totalDays = prevAtt.totalDays + 1,
      presentDays = prevAtt.presentDays
    )
    attendanceDao.insert(att)
    reloadFromDb()
  }

  fun formattedCurrentAttendance(presentDays: Int, totalDays: Int): String {
    val percentage = ((presentDays.toDouble() / totalDays.toDouble()) * 100)
    val df = DecimalFormat("##.##")
    df.roundingMode = RoundingMode.FLOOR

    val formattedPercentage =  df.format(percentage)
    return "$presentDays/$totalDays = $formattedPercentage%"
  }

  fun deleteFromDB(attendance: Attendance) {
    attendanceDao.deleteAttendance(attendance)
    handleSearch()
  }

  fun handleSearch() {
    if(inputSearchQuery.value.isNotBlank()) _historyAttendance.value = attendanceDao.search(inputSearchQuery.value).toList().filter { it.totalDays != 0 }
    else reloadFromDb()
  }
}