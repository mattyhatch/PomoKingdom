package com.example.pomokingdom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pomokingdom.R

class ItemShopAdapter(private var itemShopList:Array<String>? = null):RecyclerView.Adapter<ItemShopViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemShopViewHolder {
        val context1 = parent.context
        val inflater1 = LayoutInflater.from(context1)
        val view1 = inflater1.inflate(R.layout.item_shop_item,parent,false)
        return ItemShopViewHolder(view1)
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