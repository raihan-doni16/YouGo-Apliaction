package com.example.yougoapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "yoga")
data class Yoga(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "scheduleName")
    val scheduleName : String = "",

    @ColumnInfo(name = "dayTime")
    val dayTime: String  ,

    @ColumnInfo(name = "poseId")
    val poseId : String


)
