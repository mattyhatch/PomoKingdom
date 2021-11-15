package com.example.pomokingdom

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pomokingdom.databinding.ActivityMainBinding
import com.example.pomokingdom.retrofit.MyService
import com.example.pomokingdom.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var myService:MyService
    internal var compositeDisposable = CompositeDisposable()

    override fun onStop() {
        compositeDisposable.clear();
        super.onStop()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val retrofit = RetrofitClient.getInstance()
        myService = retrofit.create(MyService::class.java)
        setContentView(binding.root)
        binding.register2.setOnClickListener {register()}
        binding.login1.setOnClickListener {login()}

    }
    private fun register() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
    private fun login() {
        if(TextUtils.isEmpty(binding.editEmail2.text.toString())) {
            Toast.makeText(this@MainActivity,"Username cannot be null or empty", Toast.LENGTH_SHORT).show()
            return
        }
        if(TextUtils.isEmpty(binding.editPassword.text.toString())) {
            Toast.makeText(this@MainActivity,"Password cannot be null or empty", Toast.LENGTH_SHORT).show()
            return
        }
        compositeDisposable.addAll(myService.loginUser(binding.editEmail2.text.toString(),binding.editPassword.text.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                if(result == "\"Login Success\"") {
                    val intent = Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "" + result, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }



}