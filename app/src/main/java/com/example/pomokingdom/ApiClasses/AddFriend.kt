package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class AddFriend(private val user_id:String? = null,
private val friend_user_name:String? = null)
