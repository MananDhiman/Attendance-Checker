package com.manandhiman.attendancechecker.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

class AttendanceHistoryViewModel(application: Application) : AndroidViewModel(application) {

  private val db = Room.databaseBuilder(
    getApplication(),
    AppDatabase::class.java, "attendance-record",
  ).allowMainThreadQueries().build()

  private val attendanceDao = db.attendanceDao()

  private val historyAttendance = mutableStateOf(attendanceDao.getAll().filter { it.totalDays != 0 })
  var historyList = historyAttendance.value.toList()
    private set

  private fun deleteFromDB(attendance: Attendance) {
    attendanceDao.deleteAttendance(attendance)
    historyAttendance.value = attendanceDao.getAll().toList()
  }

  fun searchHistory(searchQuery: String) {
    historyAttendance.value = attendanceDao.search(searchQuery).toList().filter { it.totalDays != 0 }
  }

  fun formattedCurrentAttendance(presentDays: Int, totalDays: Int): String {
    val percentage = ((presentDays.toDouble() / totalDays.toDouble()) * 100)
    val df = DecimalFormat("##.##")
    df.roundingMode = RoundingMode.FLOOR

    val formattedPercentage =  df.format(percentage)
    return "$presentDays/$totalDays = $formattedPercentage%"
  }

}