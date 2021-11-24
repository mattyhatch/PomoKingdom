package com.example.pomokingdom

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pomokingdom.R

class ItemShopViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
    private var itemName = itemView.findViewById<TextView>(R.id.item_name)
    private val editPrice =  itemView.findViewById<TextView>(R.id.editPrice)

    fun bind(item:String) {
        //Still to implement with api call
        bindOrHide(itemName,item)
        bindOrHide(editPrice,"1")
    }

    fun bindOrHide(textView:TextView,data:String?) {
        if(data == null) {
            textView.visibility = View.GONE
        } else {
            textView.text = data
            textView.visibility = View.VISIBLE
        }
    }
}