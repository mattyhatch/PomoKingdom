package com.example.pomokingdom.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.FieldNamingPolicy

import com.google.gson.GsonBuilder

import com.google.gson.Gson
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.DateFormat


object RetrofitClient {
    private var instance: Retrofit? = null

    fun getInstance():Retrofit {
        if(instance == null) {
            instance = Retrofit.Builder()
                .baseUrl("http://129.8.226.113:5000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
    }
}