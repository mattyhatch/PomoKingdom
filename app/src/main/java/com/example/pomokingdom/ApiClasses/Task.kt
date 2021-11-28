package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class Task(private var _id:String? = null,
var name:String)
