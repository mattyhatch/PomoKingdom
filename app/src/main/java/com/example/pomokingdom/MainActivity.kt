package com.example.pomokingdom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.pomokingdom.ApiClasses.AuthUser
import com.example.pomokingdom.databinding.ActivityMainBinding
import com.example.pomokingdom.retrofit.MyService
import com.example.pomokingdom.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var myService:MyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val retrofit =  RetrofitClient.getInstance()
        myService = retrofit.create(MyService::class.java)
        setContentView(binding.root)
        binding.register2.setOnClickListener {register()}
        binding.login1.setOnClickListener {login()}
        binding.bypass.setOnClickListener{bypass()}
    }
    private fun register() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun login() {
        if (TextUtils.isEmpty(binding.editEmail2.text.toString())) {
            Toast.makeText(
                this@MainActivity,
                "Username cannot be null or empty",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (TextUtils.isEmpty(binding.editPassword.text.toString())) {
            Toast.makeText(
                this@MainActivity,
                "Password cannot be null or empty",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val userStr = binding.editEmail2.text.toString()
        val passStr = binding.editPassword.text.toString()
        val auth = AuthUser(userStr,passStr)
        //Not quite working with database yet, use bypass button to test HomeActivity and beyond functionality
        val call = myService.apiGetUsers()
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.code() != 200) {
                    Toast.makeText(this@MainActivity, "Failed to respond", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                val response = response.body()
                val lmao = 5

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to connect", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun bypass() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}