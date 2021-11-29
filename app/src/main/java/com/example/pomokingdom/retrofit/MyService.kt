package com.example.pomokingdom.retrofit


import com.example.pomokingdom.ApiClasses.*
import retrofit2.Call
import retrofit2.http.*

interface MyService {

    @Headers("Content-Type: application/json")
    @POST("/api/v1/users/")
    fun apiAddUser(@Body userData: userData): Call<String>

    @GET("/api/v1/users/")
    fun apiGetUsers() : Call<String>

    @Headers("Content-Type: application/json")
    @PUT("/api/v1/users/")
    fun apiAuthUser(@Body authUser: AuthUser):Call<String>

    @Headers("Content-Type: application/json")
    @PUT("/api/v1/users/")
    fun apiUpdateUser(@Body UpdateUser:UpdateUser):Call<String>

    @GET("/api/v1/users/tasks/")
    fun apiGetTasks(@Query("userId")id:String?):Call<String>

    @Headers("Content-Type: application/json")
    @POST("/api/v1/users/tasks/")
    fun apiAddTask(@Body AddTask: AddTask):Call<String>

    @Headers("Content-Type: application/json")
    @PUT("/api/v1/users/tasks/")
    fun apiUpdateTask(@Body UpdateTask: UpdateTask):Call<String>


    @HTTP(method = "DELETE",path = "/api/v1/users/tasks/",hasBody = true)
    fun apiDeleteTask(@Body DeleteTask:DeleteTask):Call<String>

    @GET("/api/v1/users/friends/")
    fun apiGetFriends(@Query("userId")id:String?):Call<String>

    @Headers("Content-Type: application/json")
    @POST("/api/v1/users/friends/")
    fun apiAddFriend(@Body AddFriend:AddFriend):Call<String>

    @HTTP(method = "DELETE",path = "/api/v1/users/friends/",hasBody = true)
    fun apiDeleteFriend(@Body DeleteFriend:DeleteFriend):Call<String>
}