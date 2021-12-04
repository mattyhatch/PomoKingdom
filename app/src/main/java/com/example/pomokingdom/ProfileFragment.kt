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
import com.example.pomokingdom.ApiClasses.ItemShopItem
import com.example.pomokingdom.ApiClasses.ItemShopItemList
import com.example.pomokingdom.ApiClasses.User
import com.example.pomokingdom.character.InventoryAdapter
import com.example.pomokingdom.databinding.FragmentProfileBinding
import com.example.pomokingdom.itemshop.ItemShopAdapter
import com.example.pomokingdom.retrofit.MyService
import com.example.pomokingdom.retrofit.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(R.layout.fragment_profile),InventoryAdapter.OnItemClickListener1 {
    private lateinit var myService: MyService
    private var user: User? = null
    private val model:UserViewModel by activityViewModels()
    private lateinit var inventoryList:ItemShopItemList
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter1: InventoryAdapter
    private lateinit var binding:FragmentProfileBinding

    override fun onStop() {
        super.onStop()
        val arr1 = mutableListOf<String>(binding.weaponEquipped.text.toString(),binding.shieldEquipped.text.toString(),binding.armorEquipped.text.toString())
        model.setArr(arr1)
        model.setFlag(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater,container,savedInstanceState)

        binding = FragmentProfileBinding.inflate(inflater)
        val retrofit = RetrofitClient.getInstance()
        myService = retrofit.create(MyService::class.java)
        user = model?.user
        val char = user?.oldUser?.getChar()
        binding.charName.text = char?.getChar()
        binding.charGold.text = char?.getStats()?.getGold()?.toString()
        binding.charLevel.text = char?.getStats()?.getLevel().toString()
        binding.charXp.text = char?.getStats()?.getXpNext().toString()
        binding.charHealth.text = char?.getStats()?.getCurrentHP().toString() + "/" + char?.getStats()?.getMaxHP().toString()
        if(model.equipFlag) {
            val arr1 = model.getArr()
            binding.weaponEquipped.text = arr1[0]
            binding.shieldEquipped.text = arr1[1]
            binding.armorEquipped.text = arr1[2]
        }
        recyclerView = binding.inventoryList
        myService.apiGetInventory(user?.oldUser?.getId()).enqueue( object:
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.code() != 200) {
                    Toast.makeText(context,"Response failed"+response.errorBody(), Toast.LENGTH_SHORT).show()
                    adapter1 = InventoryAdapter(mutableListOf(),this@ProfileFragment,user)
                    setUpRecyclerView()
                    return
                }
                val str  = response.body()
                val gson = Gson()
                inventoryList = gson.fromJson(str, ItemShopItemList::class.java)
                adapter1 = InventoryAdapter(inventoryList.getList(),this@ProfileFragment,user)
                setUpRecyclerView()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context,"Failed to connect,Item Shop will be empty", Toast.LENGTH_SHORT).show()
                adapter1 = InventoryAdapter(mutableListOf(),this@ProfileFragment,user)
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

    override fun onItemClick(position: Int) {
        val list = inventoryList.getList()
        val item = list?.get(position)
        if(item.getType() == "weapon") {
            binding.weaponEquipped.text = item.getName()
        } else if(item.getType() == "shield") {
            binding.shieldEquipped.text = item.getName()

        } else if(item.getType() == "armor") {
            binding.armorEquipped.text = item.getName()
        }
        val str = binding.armorEquipped.text.toString()
        val str1 = binding.shieldEquipped.text.toString()
        val str2 = binding.weaponEquipped.text.toString()
        var gCounter = 0
        var wCounter = 0
        if(str.subSequence(0,3) =="Gold") {
            gCounter++
        }
        if(str1.subSequence(0,3) =="Gold") {
            gCounter++
        }
        if(str2.subSequence(0,3) =="Gold") {
            gCounter++
        }
        if(str.subSequence(0,3) =="Wood") {
            wCounter++
        }
        if(str1.subSequence(0,3) =="Wood") {
            wCounter++
        }
        if(str2.subSequence(0,3) =="Wood") {
            wCounter++
        }
        model.setWoodEquipped(wCounter)
        model.setGoldEquipped(gCounter)
    }
}