package com.example.pomokingdom.ApiClasses

import com.google.gson.annotations.SerializedName

class oldUser {
    @SerializedName("user_name")private var user_name :String? = null
    @SerializedName("password")private var password:String? = null
    @SerializedName("friends_list")private var fList:FriendsList? = null
    @SerializedName("character")private var char:Character1? = null
    @SerializedName("tasks")private var tasks:Array<String>? = null
    @SerializedName("date_created")private var date_created:String? = null

    fun getUsername():String? {
        return user_name
    }
    fun setUsername(name:String?) {
        user_name = name
    }
    fun getPassword():String? {
        return password
    }
    fun setPassword(pass:String?) {
        password = pass
    }
    fun getChar():Character1? {
        return char
    }
    fun setChar(char1:Character1?) {
        char = char1
    }
    fun getFriends():FriendsList? {
        return fList
    }
    fun getFriends(list:FriendsList?) {
        fList = list
    }
    fun getTasks():Array<String>? {
        return tasks
    }
    fun setTasks(temp:Array<String>?) {
        tasks = temp
    }
    fun getDate() : String? {
        return date_created
    }
    fun setDate(date:String?) {
        date_created = date
    }
}