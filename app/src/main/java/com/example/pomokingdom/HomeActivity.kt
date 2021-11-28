package com.example.pomokingdom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.pomokingdom.ApiClasses.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.serialization.json.Json

class HomeActivity : AppCompatActivity() {
    private val model:UserViewModel by viewModels()
    var flag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val itemshopFragment = ItemShopFragment()
        val friendsFragment = FriendsFragment()
        val bool:Boolean = intent.getBooleanExtra("flag",true)
        if(bool) {
            val str = intent.getStringExtra("user")
            val gson = Gson()
            val user1 = gson.fromJson(str,User::class.java)
            model.loadUser(user1)
            flag = false
        }
        setCurrentFragment(homeFragment)
        val nav = findViewById<BottomNavigationView>(R.id.nav_bottom)
        nav?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    setCurrentFragment(homeFragment)
                    true
                }
                R.id.profile1 -> {
                    setCurrentFragment(profileFragment)
                    true
                }
                R.id.itemshop -> {
                    setCurrentFragment(itemshopFragment)
                    true
                }
                R.id.friends -> {
                    setCurrentFragment(friendsFragment)
                    true
                }
                else -> false
            }
        }
    }
    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_layout, fragment)
            commit()
        }
    }
}