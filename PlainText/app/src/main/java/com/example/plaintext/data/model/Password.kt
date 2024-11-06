package com.example.plaintext.data.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlin.reflect.KProperty

// Implemente a classe Password e PasswordInfo
// Password deve ser uma entidade do Room
// PasswordInfo deve ser uma classe de dados serializável


@Entity(tableName = "passwords")
data class Password(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val login: String,
    val password: String,
    val notes: String? = null
)