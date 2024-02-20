package com.example.mybook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class book_Factory (private val repository : book_Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return book_ViewModel(repository) as T
    }

}
