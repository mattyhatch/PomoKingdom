package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class FriendsList(private var num_friends:Int,
        private var friends:Array<Friend>) {


    fun getNumFriends():Int {
        return num_friends
    }
    fun getFriendsList():Array<Friend> {
        return friends
    }
}