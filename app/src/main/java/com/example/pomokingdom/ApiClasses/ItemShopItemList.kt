package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class ItemShopItemList(private val list:MutableList<ItemShopItem>) {
    fun getList():MutableList<ItemShopItem>{
        return list
    }
}
