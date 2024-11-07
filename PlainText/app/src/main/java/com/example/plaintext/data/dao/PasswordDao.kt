package com.example.plaintext.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.plaintext.data.model.Password
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(password: Password)

    @Update
    suspend fun update(password: Password)

    @Delete
    suspend fun delete(password: Password)

    @Query("SELECT * FROM passwords WHERE id = :id")
    fun getPasswordById(id: Int): Flow<Password>

    @Query("SELECT * FROM passwords")
    fun getAllPasswords(): Flow<List<Password>>
}

