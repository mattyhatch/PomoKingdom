package com.example.pomokingdom.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pomokingdom.R

class FriendsListAdapter(private var friendsList:Array<String>? = null):RecyclerView.Adapter<FriendsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.friend_list_item,parent,false)
        return FriendsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        holder.bind(friendsList!![position])
    }

    override fun getItemCount():Int {
        return if (friendsList == null) 0 else friendsList!!.size
    }

    fun setList(list:Array<String>?) {
        friendsList = list
        notifyDataSetChanged()
    }

}