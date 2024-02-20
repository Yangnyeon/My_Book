package com.example.mybook.advice

import retrofit2.Response
import retrofit2.http.GET

interface advice_Api {

    @GET("advice")
    suspend fun getAdvice ()
            : Response<Adivce_Data>

}