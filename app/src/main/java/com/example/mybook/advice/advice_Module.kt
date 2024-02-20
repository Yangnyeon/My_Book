package com.example.mybook.advice

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class advice_Module {

    companion object {
        @Singleton
        @Provides
        fun getRetroInstance(): Retrofit {

            return Retrofit.Builder()
                .baseUrl(advice_Address.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Singleton
        @Provides
        fun getPost() : advice_Api {

            //return Cancer_Instance.api.getAlbums(perpage,per,current)
            return getRetroInstance().create(advice_Api::class.java)
        }

    }


}