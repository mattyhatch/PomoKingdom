package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class GetUser(private val users: Array<User>) {
    fun getUser():User {
        return users.first()
    }
}
