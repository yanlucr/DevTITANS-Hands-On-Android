package com.example.plaintext.data.repository

import com.example.plaintext.data.dao.PasswordDao
import com.example.plaintext.data.model.Password
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PasswordDBStore @Inject constructor(
    private val passwordDao: PasswordDao
) {
    suspend fun insertPassword(password: Password) {
        passwordDao.insert(password)
    }

    suspend fun updatePassword(password: Password) {
        passwordDao.update(password)
    }

    suspend fun deletePassword(password: Password) {
        passwordDao.delete(password)
    }

    fun getPasswordById(id: Int): Flow<Password> {
        return passwordDao.getPasswordById(id)
    }

    fun getAllPasswords(): Flow<List<Password>> {
        return passwordDao.getAllPasswords()
    }
}
