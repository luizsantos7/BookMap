package com.example.bookmap.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bookmap.data.entity.enum.ReadStatus

@Entity(tableName = "books")
data class FavoriteBookEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val coverUrl: String?,
    val isRead: ReadStatus = ReadStatus.UNREAD,
)