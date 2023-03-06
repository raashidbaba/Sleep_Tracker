package com.example.sleeptracker.Database

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.internal.synchronized


@Database(entities = [SleepNight::class], version = 1, exportSchema = false)

abstract class SleepDatabase : RoomDatabase() {
    abstract val sleepDatabaseDao: SleepDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: SleepDatabase? = null
        fun getInstance(context: Context): SleepDatabase {
            kotlin.synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SleepDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}