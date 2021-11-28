package com.example.pomokingdom.ApiClasses

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AddTask(@SerializedName("user_id")private val user_id:String? = null,
                   @SerializedName("task_name")private val task_name:String? = null)
