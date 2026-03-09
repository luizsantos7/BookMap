package com.example.bookmap.di

import com.example.bookmap.data.repository.BookRepository
import com.example.bookmap.data.repository.BookRepositoryImpl
import com.example.bookmap.data.repository.FavoriteRepository
import com.example.bookmap.data.repository.FavoriteRepositoryImpl
import com.example.bookmap.data.repository.StatusRepository
import com.example.bookmap.data.repository.StatusRepositoryImpl
import com.example.bookmap.data.repository.UserRepository
import com.example.bookmap.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBookRepository(
        bookRepositoryImpl: BookRepositoryImpl
    ): BookRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(
        favoriteRepositoryImpl: FavoriteRepositoryImpl
    ): FavoriteRepository

    @Binds
    @Singleton
    abstract fun bindStatusRepository(
        statusRepositoryImpl: StatusRepositoryImpl
    ): StatusRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}