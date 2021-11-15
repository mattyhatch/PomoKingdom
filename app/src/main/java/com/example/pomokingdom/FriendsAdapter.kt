package com.example.pomokingdom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class FriendsAdapter: RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {

    //testing
    private var test = arrayOf("test1", "teat2", "test3", "test4", "test5")

    class FriendsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var friendName: TextView

        init {
            friendName = itemView.findViewById(R.id.friend_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.friend_card,parent,false)
        return FriendsViewHolder(v)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        holder.friendName.text = test[position]
    }

    override fun getItemCount(): Int {
        return test.size
    }


}