package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUser(private val user_name:String? = null,
private val password:String? = null,
private val id:String? = null) {

}
