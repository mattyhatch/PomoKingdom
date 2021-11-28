package com.example.pomokingdom.ApiClasses

import com.auth0.android.jwt.JWT
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerializedName("result") var oldUser:oldUser? = null,
    @SerializedName("token") var token: String? = null
)