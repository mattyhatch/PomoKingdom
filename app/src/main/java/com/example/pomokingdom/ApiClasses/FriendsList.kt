package com.example.pomokingdom.ApiClasses

class FriendsList {
    private var num_friends:Int? = 0
    private var friends:Array<String>? = null


    fun getNumFriends():Int? {
        return num_friends
    }
    fun getFriendsList():Array<String>? {
        return friends
    }
}