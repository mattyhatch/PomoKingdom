package com.example.pomokingdom.ApiClasses

import com.google.gson.annotations.SerializedName

data class AuthUser(
    @SerializedName("user_name") val userName:String?,
    @SerializedName("password") val password:String?,
    @SerializedName("auth") val auth:Int? = 1
)
