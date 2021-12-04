package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class BuyItem(private val name:String?,
                    private val type:String?) {
    fun getName():String? {
        return name
    }
    fun getType():String? {
        return type
    }
}
