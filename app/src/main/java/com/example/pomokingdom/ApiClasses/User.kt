package com.example.pomokingdom.ApiClasses

import com.auth0.android.jwt.JWT
import com.google.gson.annotations.SerializedName


class User {
    @SerializedName("oldUser") private var oldUser:oldUser? = null
    @SerializedName("token") private var token: JWT? = null
}