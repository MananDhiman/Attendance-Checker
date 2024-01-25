package com.manandhiman.attendancechecker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.manandhiman.attendancechecker.model.Attendance


@Dao
interface AttendanceDao {
  @Query("SELECT * FROM attendance ORDER BY ID DESC")
  fun getAll(): List<Attendance>

  @Query("SELECT * FROM attendance WHERE subject_name=:subjectName ORDER BY id DESC LIMIT 1 ")
  fun getLastById(subjectName: String): Attendance

  @Query("SELECT max(id) as id,subject_name,date,status,total_days,present_days from attendance group by subject_name;")
  fun getLastBySubject(): List<Attendance>

  @Insert
  fun insert(attendance: Attendance)

  @Delete
  fun deleteAttendance(attendance: Attendance)

  @Query("SELECT * FROM attendance WHERE subject_name LIKE ('%'||:searchQuery||'%') OR date LIKE('%'||:searchQuery||'%') ORDER BY id DESC;")
  fun search(searchQuery: String): List<Attendance>
}