package com.example.pomokingdom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val itemshopFragment = ItemShopFragment()
        val friendsFragment = FriendsFragment()

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