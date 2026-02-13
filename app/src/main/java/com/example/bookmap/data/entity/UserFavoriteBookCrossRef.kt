package com.example.bookmap.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "user_favorite_books",
    primaryKeys = ["userId", "bookId"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(entity = FavoriteBookEntity::class, parentColumns = ["id"], childColumns = ["bookId"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index("userId"), Index("bookId")]
)
data class UserFavoriteBookCrossRef(
    val userId: Int,
    val bookId: Int
)

data class UserWithFavoriteBooks(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = UserFavoriteBookCrossRef::class,
            parentColumn = "userId",
            entityColumn = "bookId"
        )
    )
    val favoriteBooks: List<FavoriteBookEntity>
)