package com.example.mybook

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book_Model::class], version = 1, exportSchema = true)
abstract class Book_Database : RoomDatabase() {

    abstract fun book_dao(): book_Dao

    companion object {
        private var instance: Book_Database? = null

        @Synchronized
        fun getInstance(context: Context): Book_Database? {
            if (instance == null) {
                synchronized(Book_Database::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        Book_Database::class.java,
                        "book_Table"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }
}