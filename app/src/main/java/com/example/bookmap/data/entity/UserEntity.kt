package com.example.bookmap.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.bookmap.data.entity.enum.CountType
import com.example.bookmap.utils.constants.EMPTY_STRING


@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    @Ignore
    val confirmEmail: String,
    val password: String,
    val birthday: String,
    val gender: String,
    var countType: String = CountType.GUEST.name,
){
    constructor(
        id: Int,
        name: String,
        email: String,
        password: String,
        birthday: String,
        gender: String,
        countType: String
    ) : this(id, name, email, "", password, birthday, gender, countType)
}