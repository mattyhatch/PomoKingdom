package com.example.pomokingdom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.pomokingdom.ApiClasses.*
import com.example.pomokingdom.databinding.FragmentFriendsBinding
import com.example.pomokingdom.friends.FriendsListAdapter
import com.example.pomokingdom.retrofit.MyService
import com.example.pomokingdom.retrofit.RetrofitClient
import com.example.pomokingdom.tasks.TaskListAdapter
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendsFragment : Fragment(R.layout.fragment_friends) {
    private lateinit var binding :FragmentFriendsBinding
    private lateinit var recyclerView:RecyclerView
    private val model:UserViewModel by activityViewModels()
    private var user: User? = null
    private lateinit var adapter1:FriendsListAdapter
    private lateinit var myService: MyService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater,container,savedInstanceState)
        binding = FragmentFriendsBinding.inflate(inflater)
        val retrofit = RetrofitClient.getInstance()
        myService = retrofit.create(MyService::class.java)
        recyclerView = binding.friendsList
        binding.addFriend.setOnClickListener { addFriend()}
        user = model.user
        myService.apiGetFriends(user?.oldUser?.getId()).enqueue(
            object:
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.code() != 200) {
                        Toast.makeText(context,"Response failed"+response.errorBody(), Toast.LENGTH_SHORT).show()
                        return
                    }
                    val str  = response.body()
                    val gson = Gson()
                    val result = gson.fromJson(str, FriendsList::class.java)
                    var friendsList = mutableListOf<String>()
                    val temp = result.getFriendsList()
                    if(temp != null) {
                        for (friends in temp) {
                            friendsList.add(friends.user_name)
                        }
                    }
                    adapter1 = FriendsListAdapter(friendsList,user)
                    setUpRecyclerView()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(context,"Failed to connect,Friends List will be empty", Toast.LENGTH_SHORT).show()
                    adapter1 = FriendsListAdapter(mutableListOf(),user)
                    setUpRecyclerView()
                }
            }
        )
        return binding.root
    }

    private fun addFriend() {
        val str = binding.editAddFriend.text.toString()
        myService.apiAddFriend(AddFriend(user?.oldUser?.getId(),str)).enqueue(
            object:
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.code() != 200) {
                        val lmao = response.errorBody()
                        Toast.makeText(context,"Response failed"+response.errorBody(), Toast.LENGTH_SHORT).show()
                        return
                    }
                    val gson = Gson()
                    val str1 = response.body()
                    val result = gson.fromJson(str1, AddTaskResult::class.java)
                    if(result.status == "success") {
                        binding.editAddFriend.setText("Add Friend", TextView.BufferType.EDITABLE)
                        adapter1.addToList(str)
                        Toast.makeText(context,"Successfully added friend", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(context,"Response failed"+response.errorBody(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(context,"Failed to connect", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
    private fun setUpRecyclerView() {
        binding.friendsList.apply {
            adapter = adapter1
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }

        val animator = binding.friendsList.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }
}