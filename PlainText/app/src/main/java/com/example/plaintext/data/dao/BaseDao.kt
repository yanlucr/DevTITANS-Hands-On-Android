package com.example.plaintext.data.dao

import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.room.Update
import com.example.plaintext.data.model.Password

@Database(entities = [Password::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun passwordDao(): PasswordDao
}