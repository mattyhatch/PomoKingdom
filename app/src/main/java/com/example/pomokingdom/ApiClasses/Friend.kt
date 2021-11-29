package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class Friend(private val _id:String? = null,
val user_name:String,
private val character:Character1? = null,
private val inventory:Array<String>? = null)
