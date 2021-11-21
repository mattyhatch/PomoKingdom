package com.example.pomokingdom.ApiClasses

import com.google.gson.annotations.SerializedName

data class userData(
    @SerializedName("user_name") val userName:String?,
    @SerializedName("password") val password:String?,
    @SerializedName("char_name") val charN:String?
)
