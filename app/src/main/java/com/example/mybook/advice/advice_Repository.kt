package com.example.mybook.advice

import retrofit2.Response

class advice_Repository {

    private val adivce_API: advice_Api

    init {
        var db = advice_Module.getPost()
        adivce_API = db!!
    }

    suspend fun getAdvice() : Response<Adivce_Data> {
        return adivce_API.getAdvice()
    }

}