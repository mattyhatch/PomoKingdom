package com.example.pomokingdom.ApiClasses

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class oldUser(@SerializedName("user_name")var user_name :String? = null,
    @SerializedName("password")private var password:String? = null,
@SerializedName("friends_list")var fList:FriendsList? = null,
@SerializedName("character")private var char:Character1? = null,
@SerializedName("tasks") private var tasks:Array<Task>? = null,
@SerializedName("date_created")private var date_created:String? = null,
@SerializedName("_id") private var id:String? = null) {

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
    fun getTasks():Array<Task>? {
        return tasks
    }
    fun setTasks(temp:Array<Task>?) {
        tasks = temp
    }
    fun getDate() : String? {
        return date_created
    }
    fun setDate(date:String?) {
        date_created = date
    }
    fun getId():String? {
        return id
    }
    fun setId(id1:String?) {
       id = id1
    }
}