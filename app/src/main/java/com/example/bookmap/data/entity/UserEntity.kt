package com.example.bookmap.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bookmap.data.entity.enum.CountType

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)  val id : Int = 0,
    val name: String,
    val email: String,
    val confirmEmail: String,
    val password: String,
    val birthday: String,
    val gender: String,
    var countType: CountType = CountType.GUEST,
    val followers: Int = 0,
    val following: Int = 0,
    @Embedded val favoritedBooks : MutableList<BookEntity> = mutableListOf()
)