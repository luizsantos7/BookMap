package com.example.bookmap.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.example.bookmap.data.entity.FavoriteBookEntity
import com.example.bookmap.data.entity.UserFavoriteBookCrossRef
import com.example.bookmap.data.entity.UserWithFavoriteBooks

@Dao
interface FavoriteDao {

    // Inserir um livro favorito (caso ele ainda não exista)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteBook(book: FavoriteBookEntity)

    // Relacionar o livro com o usuário
    @Insert(onConflict = REPLACE)
    suspend fun insertUserFavoriteCrossRef(crossRef: UserFavoriteBookCrossRef)

    // Remover o favoritamento
    @Delete
    suspend fun deleteUserFavoriteCrossRef(crossRef: UserFavoriteBookCrossRef)

    // Obter todos os livros favoritos de um usuário
    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserWithFavoriteBooks(userId: Int): UserWithFavoriteBooks?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserFavoriteBook(crossRef: UserFavoriteBookCrossRef)

    @Query("DELETE FROM user_favorite_books WHERE userId = :userId AND bookId = :bookId")
    suspend fun deleteUserFavoriteBook(userId: Int, bookId: Int)

}