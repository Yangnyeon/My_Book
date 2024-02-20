package com.example.mybook

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface book_Dao {

    @Query("SELECT * FROM book_Table ORDER BY id DESC")
    fun getAll(): LiveData<List<Book_Model>>

    @Insert
    fun insert(cloths: Book_Model)

    @Delete
    fun delete(cloths: Book_Model)

    @Query("SELECT * FROM book_Table WHERE book_Edit LIKE '%' || :searchQuery || '%' ORDER BY id DESC")
    fun searchDatabase(searchQuery: String): Flow<List<Book_Model>>


}