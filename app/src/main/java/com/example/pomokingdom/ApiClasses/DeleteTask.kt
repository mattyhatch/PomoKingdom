package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class DeleteTask(private val user_id:String? = null,
private val task_name:String? = null)
