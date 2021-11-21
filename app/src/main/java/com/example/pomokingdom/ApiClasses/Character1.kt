package com.example.pomokingdom.ApiClasses

class Character1 {
    private var char_name:String? = null
    private var stats:Stat? = null

    fun getChar():String? {
        return char_name
    }
    fun getStats():Stat? {
        return stats
    }
}