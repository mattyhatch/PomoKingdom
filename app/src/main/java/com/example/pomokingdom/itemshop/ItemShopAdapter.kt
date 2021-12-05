package com.example.pomokingdom.itemshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pomokingdom.ApiClasses.*
import com.example.pomokingdom.R
import com.example.pomokingdom.UserViewModel
import com.example.pomokingdom.character.InventoryAdapter
import com.example.pomokingdom.retrofit.MyService
import com.example.pomokingdom.retrofit.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemShopAdapter(
    private var itemList:MutableList<ItemShopItem>? = null,
    private val user: User? = null,
    private val model: UserViewModel
) : RecyclerView.Adapter<ItemShopAdapter.ItemShopViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemShopViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_shop_item,parent,false)
        return ItemShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemShopViewHolder, position: Int) {
        holder.bind(itemList!![position])
    }

    override fun getItemCount():Int {
        return if (itemList == null) 0 else itemList!!.size
    }

    fun setList(list:MutableList<ItemShopItem>?) {
        itemList = list
        notifyDataSetChanged()
    }
    fun addToList(task: ItemShopItem) {
        itemList?.add(task)
        notifyDataSetChanged()
    }

    inner class ItemShopViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var itemName: TextView = itemView.findViewById(R.id.itemName1)
        private var itemType: TextView = itemView.findViewById(R.id.itemType1)
        private var itemCost:TextView = itemView.findViewById((R.id.itemCost))
        private var buy: Button = itemView.findViewById(R.id.buy)

        init {
            buy.setOnClickListener{buy(this)}
        }


        private fun buy(holder: ItemShopViewHolder) {
            val position = holder.adapterPosition
            val item = itemList?.get(position)
            val retrofit = RetrofitClient.getInstance()
            val myService = retrofit.create(MyService::class.java)
            val userGold = user?.oldUser?.getChar()?.getStats()?.getGold()!!
            val itemCost = item?.getCost()
            val compare = itemCost?.let { userGold?.compareTo(it) }
            if (compare != null) {
                if (compare >= 0) {
                    myService.apiAddInventory(AddInventory(user?.oldUser?.getId(), item?.getId()))
                        .enqueue(
                            object : Callback<String> {
                                override fun onResponse(
                                    call: Call<String>,
                                    response: Response<String>
                                ) {
                                    if(response.code() == 400) {
                                        Toast.makeText(
                                            itemView.context,
                                            "Already Bought Item",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return
                                    }
                                    if (response.code() != 200) {
                                        Toast.makeText(
                                            itemView.context,
                                            "Failed to buy item",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return
                                    }
                                    val str = response.body()
                                    val gson = Gson()
                                    val result = gson.fromJson(str,AddTaskResult::class.java)
                                    if(result.status == "success") {
                                        val gold1 = userGold - itemCost
                                        myService.apiUpdateCharGold(UpdateCharGold(user?.oldUser?.getId(), gold1)).enqueue (
                                            object: Callback<String> {
                                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                                    if(response.code() != 200) {
                                                        Toast.makeText(itemView.context,"Failed to update",Toast.LENGTH_SHORT)
                                                        return
                                                    }
                                                    val str = response.body()
                                                    val gson = Gson()
                                                    val result = gson.fromJson(str,AddTaskResult::class.java)
                                                    if( result.status == "success") {
                                                        myService.apiGetUser(user?.oldUser?.getId()).enqueue(
                                                            object: Callback<String> {
                                                                override fun onResponse(
                                                                    call: Call<String>,
                                                                    response: Response<String>
                                                                ) {
                                                                    if(response.code() != 200) {
                                                                        Toast.makeText(itemView.context,"Failed to update",Toast.LENGTH_SHORT)
                                                                    }
                                                                    val str = response.body()
                                                                    val gson = Gson()
                                                                    val result = gson.fromJson(str,oldUser::class.java)
                                                                    model.loadUser(User(result,user.token))
                                                                }

                                                                override fun onFailure(call: Call<String>, t: Throwable) {
                                                                    Toast.makeText(itemView.context,"Failed to update",Toast.LENGTH_SHORT)
                                                                }
                                                            }
                                                        )
                                                    }
                                                }

                                                override fun onFailure(call: Call<String>, t: Throwable) {
                                                    Toast.makeText(itemView.context,"Failed to update",Toast.LENGTH_SHORT)
                                                }
                                            }
                                        )
                                    }
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Toast.makeText(
                                        itemView.context,
                                        "Failed to buy item",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })

                } else {
                    Toast.makeText(
                        itemView.context,
                        "Not enough gold",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    itemView.context,
                    "Error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        fun bind(task: ItemShopItem) {
            bindOrHide(task)
        }

        fun bindOrHide(data: ItemShopItem?) {
            if(data == null) {
                itemName.visibility = View.GONE
                itemType.visibility = View.GONE
                itemCost.visibility = View.GONE
            } else {
                itemName.text = data.getName()
                itemType.text = data.getType()
                itemCost.text = data.getCost().toString();
                itemName.visibility = View.VISIBLE
                itemType.visibility = View.VISIBLE
                itemCost.visibility = View.VISIBLE
            }
        }
    }
}