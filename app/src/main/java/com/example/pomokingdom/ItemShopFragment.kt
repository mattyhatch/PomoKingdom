package com.example.pomokingdom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.pomokingdom.ApiClasses.*
import com.example.pomokingdom.databinding.FragmentItemShopBinding
import com.example.pomokingdom.itemshop.ItemShopAdapter
import com.example.pomokingdom.retrofit.MyService
import com.example.pomokingdom.retrofit.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ItemShopFragment : Fragment(R.layout.fragment_item_shop) {
    private lateinit var binding :FragmentItemShopBinding
    private lateinit var myService: MyService
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter1:ItemShopAdapter
    private lateinit var itemList:ItemShopItemList
    private var user: User? = null
    private val model:UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater,container,savedInstanceState)
        binding = FragmentItemShopBinding.inflate(inflater)
        val retrofit = RetrofitClient.getInstance()
        myService = retrofit.create(MyService::class.java)
        recyclerView = binding.itemList1
        user = model.user
        binding.goldCount.text = user?.oldUser?.getChar()?.getStats()?.getGold()?.toString()
        myService.apiGetItemShop().enqueue(
            object:
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.code() != 200) {
                        Toast.makeText(context,"Response failed"+response.errorBody(), Toast.LENGTH_SHORT).show()
                        return
                    }
                    val str  = response.body()
                    val gson = Gson()
                    itemList = gson.fromJson(str, ItemShopItemList::class.java)
                    adapter1 = ItemShopAdapter(itemList.getList(),user,model)
                    setUpRecyclerView()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(context,"Failed to connect,Item Shop will be empty", Toast.LENGTH_SHORT).show()
                    adapter1 = ItemShopAdapter(mutableListOf(),user,model)
                    setUpRecyclerView()
                }
            }
        )
        return binding.root
    }

    private fun setUpRecyclerView() {
        recyclerView.apply {
            adapter = adapter1
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }

        val animator = recyclerView.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }
}