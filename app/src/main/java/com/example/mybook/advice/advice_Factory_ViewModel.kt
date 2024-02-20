package com.example.mybook.advice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class advice_Factory_ViewModel (private val repository : advice_Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return advice_ViewModel(repository) as T
    }
}