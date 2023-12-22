package com.example.yougoapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.DatabaseView
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.RoomSQLiteQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import javax.sql.DataSource
@Dao
interface YogaDao {
    @Query("SELECT * FROM yoga")
    fun getAll(): LiveData<List<Yoga>>

    @Query("SELECT * FROM yoga WHERE dayTime = :day")
    fun getTodaySchedule(day: Int): List<Yoga>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(course: Yoga)
    @Update
    fun updateFav(favoriteNote: Yoga)
    @Query("DELETE from yoga WHERE poseId = :scheduleName")
    fun deleteFav(scheduleName: String)
}