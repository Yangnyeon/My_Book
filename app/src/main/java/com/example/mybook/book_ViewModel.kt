package com.example.mybook

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class book_ViewModel
@Inject constructor(private val repository : book_Repository) : AndroidViewModel(
    Application()
) {

    private var _currentData = MutableLiveData<List<Book_Model>>()


    fun insert(cigarette: Book_Model) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(cigarette)
        }
    }

    fun delete(cigarette: Book_Model){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(cigarette)
        }
    }

    fun getAll(): LiveData<List<Book_Model>> {
        return repository.getAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Book_Model>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }


}