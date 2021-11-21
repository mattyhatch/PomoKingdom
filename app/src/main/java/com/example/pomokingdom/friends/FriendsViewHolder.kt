package com.example.pomokingdom.friends

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pomokingdom.R

class FriendsViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
    private var friendName = itemView.findViewById<TextView>(R.id.friend_name)
    private val editLevel =  itemView.findViewById<TextView>(R.id.editLevel)

    fun bind(friend:String) {
        //Still to implement with api call
        bindOrHide(friendName,friend)
        bindOrHide(editLevel,"1")
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