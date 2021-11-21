package com.example.pomokingdom

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pomokingdom.ApiClasses.userData
import com.example.pomokingdom.databinding.ActivityLoginBinding
import com.example.pomokingdom.retrofit.MyService
import com.example.pomokingdom.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var myService: MyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofit = RetrofitClient.getInstance()
        myService = retrofit.create(MyService::class.java)
        binding.register1.setOnClickListener { register() }
        binding.btnLogin.setOnClickListener { login() }
    }

    private fun login() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun doNothing() {

    }

    private fun register() {
        if (binding.editPassword.text.toString() == binding.editCPassword.text.toString()) {
            var userNamestr = binding.editUsername1.text.toString()
            var passwordStr = binding.editPassword.text.toString()
            var charStr = binding.editChar.text.toString()
            val user= userData(userNamestr,passwordStr,charStr)
            myService.apiAddUser(user).enqueue(object: Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.code() != 200) {
                        Toast.makeText(this@LoginActivity,"Try a different username", Toast.LENGTH_SHORT).show()
                        return
                    }
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@LoginActivity,"Failed to connect", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Passwords don't match!")
            builder.setMessage("Please review your password and confirm password and make sure that they match")
            builder.setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { dialog, id -> doNothing() })
            val alert = builder.create()
            alert.show()
        }
    }
}

