package com.example.yougoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Yoga::class], version = 1, exportSchema = false)
abstract class YogaDatabase : RoomDatabase() {

    abstract fun yogaDao(): YogaDao

    companion object {

        @Volatile
        private var instance: YogaDatabase? = null

        fun getInstance(context: Context): YogaDatabase {
            return synchronized(this){
                instance ?: Room.databaseBuilder(context, YogaDatabase::class.java, "yoga.db")
                    .build()
            }
        }

    }
}