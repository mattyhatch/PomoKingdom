package com.example.pomokingdom.retrofit

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
interface MyService {
    @POST("register")
    @FormUrlEncoded
    fun registerUser(@Field("email") email:String,
                     @Field("username") username:String,
                     @Field("password") password:String):Observable<String>
    @POST("login")
    @FormUrlEncoded
    fun loginUser(@Field("email") email:String,
                  @Field("password") password:String): Observable<String>
}