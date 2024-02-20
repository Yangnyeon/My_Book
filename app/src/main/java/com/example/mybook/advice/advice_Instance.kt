package com.example.mybook.advice

import com.example.mybook.advice.advice_Address.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object advice_Instance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : advice_Api by lazy {
        retrofit.create(advice_Api::class.java)
    }


}