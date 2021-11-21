package com.example.pomokingdom.ApiClasses

class Stat {
    private var level:Int? = null
    private var xp_to_next_level:Int? = null
    private var max_hp:Int? = null
    private var current_hp:Int? = null
    private var gold:Int? = null

    fun getLevel():Int? {
        return level
    }
    fun getXpNext():Int?  {
        return xp_to_next_level
    }
    fun getMaxHP():Int? {
        return max_hp
    }
    fun getCurrentHP():Int? {
        return current_hp
    }
    fun getGold():Int? {
        return gold
    }
}