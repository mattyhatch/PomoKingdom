package com.example.pomokingdom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pomokingdom.ApiClasses.User
import com.example.pomokingdom.retrofit.MyService
import com.example.pomokingdom.retrofit.RetrofitClient

class UserViewModel: ViewModel() {
    private var user1:MutableLiveData<User> = MutableLiveData<User>()
    val user:User? get() = user1.value
    private var woodEquipped:Int = 0
    private var goldEquipped:Int = 0
    private var arrStr:MutableList<String> = mutableListOf()
    private var task:String ="None"
    var equipFlag:Boolean = false

    fun loadUser(user2:User) {
        user1?.value = user2
    }

    fun getWoodEquipped():Int{
        return woodEquipped
    }

    fun setWoodEquipped(num:Int) {
        woodEquipped = num
    }

    fun getGoldEquipped():Int{
        return goldEquipped
    }

    fun setGoldEquipped(num:Int) {
        goldEquipped = num
    }

    fun setArr(arr1:MutableList<String>) {
        arrStr = arr1
    }

    fun getArr():MutableList<String> {
        return arrStr
    }

    fun setFlag(boolean: Boolean) {
        equipFlag = boolean
    }

    fun setTask(str:String) {
        task = str
    }

    fun getTask():String {
        return task
    }
}
