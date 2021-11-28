package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class AddTaskResult(val status:String? = null,
private val response:MongoResponse? = null)
