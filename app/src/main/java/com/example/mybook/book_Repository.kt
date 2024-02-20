package com.example.mybook

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class book_Repository
@Inject
constructor(application: Application) {


    private val book_dao: book_Dao
    init {
        var db = book_Module.get_vlog_DB(application)
        book_dao = db!!.book_dao()
    }

    fun insert(vlog_Model: Book_Model) {
        book_dao.insert(vlog_Model)
    }

    fun delete(vlog_Model: Book_Model){
        book_dao.delete(vlog_Model)
    }

    fun getAll(): LiveData<List<Book_Model>> {
        return book_dao.getAll()
    }

    fun searchDatabase(searchQuery: String): Flow<List<Book_Model>> {
        return book_dao.searchDatabase(searchQuery)
    }

}