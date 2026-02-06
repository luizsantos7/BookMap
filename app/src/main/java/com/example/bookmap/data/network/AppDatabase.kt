package com.example.bookmap.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookmap.data.dao.UserDao
import com.example.bookmap.data.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
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