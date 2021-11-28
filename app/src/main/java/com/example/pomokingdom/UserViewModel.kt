package com.example.pomokingdom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pomokingdom.ApiClasses.User
import com.example.pomokingdom.retrofit.MyService
import com.example.pomokingdom.retrofit.RetrofitClient

class UserViewModel: ViewModel() {
    private val user1:MutableLiveData<User> = MutableLiveData<User>()
    val user:User? get() = user1.value

    fun loadUser(user2:User) {
        user1?.value = user2
    }
}
