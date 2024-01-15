package com.manandhiman.attendancechecker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance")
data class Attendance(
  @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int,
  @ColumnInfo(name = "subject_name") val subjectName: String,
  @ColumnInfo(name = "date") val date: String,
  @ColumnInfo(name = "status") val status: String,
  @ColumnInfo(name = "total_days") val totalDays: Int,
  @ColumnInfo(name = "present_days") val presentDays: Int
) {
  constructor(
    subjectName: String, date: String, status: String, totalDays: Int, presentDays: Int
  ) : this(
    0, subjectName, date, status, totalDays, presentDays
  )
}