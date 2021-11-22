package com.example.pomokingdom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pomokingdom.R

class ItemShopAdapter(private var itemShopList:Array<String>? = null):RecyclerView.Adapter<ItemShopViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemShopViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.friend_list_item,parent,false)
        return ItemShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemShopViewHolder, position: Int) {
        holder.bind(itemShopList!![position])
    }

    override fun getItemCount():Int {
        return if (itemShopList == null) 0 else itemShopList!!.size
    }

    fun setList(list:Array<String>?) {
        itemShopList = list
        notifyDataSetChanged()
    }

}