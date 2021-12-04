package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class Character1(private var char_name:String? = null,
        private var stats:Stat? = null,
        private var inventory:Array<ItemShopItem>? = null) {

    fun getChar():String? {
        return char_name
    }
    fun getStats():Stat? {
        return stats
    }
    fun getInventory():Array<ItemShopItem>? {
        return inventory
    }
}