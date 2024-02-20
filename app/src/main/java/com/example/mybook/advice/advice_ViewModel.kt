package com.example.mybook.advice

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class advice_ViewModel @Inject constructor(private val repository : advice_Repository) : ViewModel() {


    val myResponse : MutableLiveData<Response<Adivce_Data>> = MutableLiveData()


    fun getAdvice() {
        viewModelScope.launch {
            val response = repository.getAdvice()
            myResponse.value = response
        }
    }

    fun getAdvice2() {
        viewModelScope.launch {
            val response = repository.getAdvice()
            myResponse.value = response
        }
    }

    fun getAdvice3() {
        viewModelScope.launch {
            val response = repository.getAdvice()
            myResponse.value = response
        }
    }


}