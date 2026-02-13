package com.example.bookmap.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bookmap.data.entity.enum.CountType
import com.example.bookmap.utils.constants.EMPTY_STRING


//data class UserEntity(
//    val id: String = EMPTY_STRING,
//    val name: String = EMPTY_STRING,
//    val email: String = EMPTY_STRING,
//    val confirmEmail: String = EMPTY_STRING,
//    val password: String = EMPTY_STRING,
//    val birthday: String = EMPTY_STRING,
//    val gender: String = EMPTY_STRING,
//    var countType: String = CountType.GUEST.name,
//    val followers: Int = 0,
//    val following: Int = 0,
//    val favoritedBooks: List<BookEntity> = emptyList()
//)

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val confirmEmail: String,
    val password: String,
    val birthday: String,
    val gender: String,
    var countType: CountType = CountType.GUEST,
    val followers: Int = 0,
    val following: Int = 0
)