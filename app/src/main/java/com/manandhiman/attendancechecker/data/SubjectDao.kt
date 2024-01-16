package com.manandhiman.attendancechecker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.manandhiman.attendancechecker.model.Subject

@Dao
interface SubjectDao {

  // todo if no join used, migrate to shared pref
  @Query("SELECT * FROM subject;")
  fun getAllSubjects(): List<Subject>

  // todo join query
  @Query("SELECT name FROM subject WHERE id=:id;")
  fun getSubject(id: Int): String

  // todo remove if not join used,
  @Insert
  fun addSubjects(subjectNames: List<Subject>)
}