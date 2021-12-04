package com.example.pomokingdom.ApiClasses

data class UpdateChar(private val user_id:String?,
private val char_name:String?,
private val level:Int?,
private val xp_to_next_level:Int?,
private val max_hp:Int?,
private val current_hp:Int?,
private val gold:Int?)
