package com.devrelm.doit.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devrelm.doit.tasks.data.TaskDao
import com.devrelm.doit.tasks.data.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = true)
abstract class DoItDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
