package com.example.pomokingdom

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pomokingdom.databinding.ActivityLoginBinding
import com.example.pomokingdom.retrofit.MyService
import com.example.pomokingdom.retrofit.RetrofitClient
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.mongo.MongoClient
import io.realm.mongodb.mongo.MongoCollection
import io.realm.mongodb.mongo.MongoDatabase
import org.bson.Document
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var myService:MyService
    internal var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofit = RetrofitClient.getInstance()
        myService = retrofit.create(MyService::class.java)
        binding.register1.setOnClickListener{register()}
        binding.btnLogin.setOnClickListener{login()}
    }

    private fun login() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
    private fun doNothing() {

    }
    private fun register() {
        if(binding.editPassword.text.toString() == binding.editCPassword.text.toString()) {
            compositeDisposable.addAll(myService.registerUser(binding.editEmail.text.toString(),binding.editUsername1.text.toString(),binding.editPassword.text.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    if(result == "\"Registration Success\"") {
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "" + result, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
        else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Passwords don't match!")
            builder.setMessage("Please review your password and confirm password and make sure that they match")
            builder.setPositiveButton("Yes",DialogInterface.OnClickListener { dialog,id -> doNothing()})
            val alert = builder.create()
            alert.show()
        }
    }
}
