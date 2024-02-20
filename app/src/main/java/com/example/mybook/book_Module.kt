package com.example.mybook

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class book_Module {

    companion object {
        @Singleton
        @Provides
        fun get_vlog_DB(context: Application): Book_Database {
            return Book_Database.getInstance(context)!!
        }

        @Singleton
        @Provides
        fun get_Dao(vlog_Database: Book_Database): book_Dao {
            return vlog_Database.book_dao()
        }
    }

}