package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class AddInventory(private val user_id:String?,
private val item_id:String?)
