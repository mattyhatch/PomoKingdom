package com.example.pomokingdom.retrofit


import com.example.pomokingdom.ApiClasses.AuthUser
import com.example.pomokingdom.ApiClasses.userData
import retrofit2.Call
import retrofit2.http.*

interface MyService {

    @Headers("Content-Type: application/json")
    @POST("/api/v1/users/")
    fun apiAddUser(@Body userData: userData): Call<String>

    @GET("/api/v1/users/")
    fun apiGetUsers() : Call<String>
}