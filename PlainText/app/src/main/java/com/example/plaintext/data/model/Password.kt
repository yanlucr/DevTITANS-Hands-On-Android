package com.example.plaintext.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

// Implemente a classe Password e PasswordInfo
// Password deve ser uma entidade do Room
// PasswordInfo deve ser uma classe de dados serializável

@Serializable
@Parcelize
@Entity(tableName = "passwords")
data class Password(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val login: String,
    val password: String,
    val notes: String? = null
) : Parcelable