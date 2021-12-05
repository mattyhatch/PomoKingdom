package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class UpdateCharGold(private val user_id:String?,
private val gold:Int?)
