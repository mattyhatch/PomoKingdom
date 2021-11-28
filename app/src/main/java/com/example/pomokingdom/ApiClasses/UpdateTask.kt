package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTask(private val user_id:String? = null,
private val task_id:String? = null,
private val new_task_name:String? = null)
