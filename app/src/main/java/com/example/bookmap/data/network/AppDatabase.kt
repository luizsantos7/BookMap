package com.example.bookmap.data.network

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookmap.data.dao.FavoriteDao
import com.example.bookmap.data.dao.UserDao
import com.example.bookmap.data.entity.FavoriteBookEntity
import com.example.bookmap.data.entity.UserEntity
import com.example.bookmap.data.entity.UserFavoriteBookCrossRef


@Database(
        entities = [
            UserEntity::class,
            FavoriteBookEntity::class,
            UserFavoriteBookCrossRef::class
        ],
        version = 4
    )
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bookmap_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}