package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class ItemShopItem(private val name:String,
                        private val _id:String,
                        private val type:String,
                        private val date:String,
                        private val cost:Int
)

{
    fun getName():String {
        return name
    }
    fun getId():String {
        return _id
    }
    fun getType():String {
        return type
    }
    fun getDate():String {
        return date
    }

    fun getCost():Int {
        return cost
    }
}
