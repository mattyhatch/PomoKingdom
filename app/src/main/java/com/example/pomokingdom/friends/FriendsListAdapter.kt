package com.example.pomokingdom.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pomokingdom.ApiClasses.AddTaskResult
import com.example.pomokingdom.ApiClasses.DeleteFriend
import com.example.pomokingdom.ApiClasses.Friend
import com.example.pomokingdom.ApiClasses.User
import com.example.pomokingdom.R
import com.example.pomokingdom.retrofit.MyService
import com.example.pomokingdom.retrofit.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendsListAdapter(private var friendsList:MutableList<String>? = null,
                         private val user: User? = null):RecyclerView.Adapter<FriendsListAdapter.FriendsViewHolder>() {

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

    fun setList(list:MutableList<String>?) {
        friendsList = list
        notifyDataSetChanged()
    }
    fun addToList(str:String) {
        friendsList?.add(str)
        notifyDataSetChanged()
    }
    inner class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var friendName = itemView.findViewById<TextView>(R.id.friend_name)
        private val editLevel =  itemView.findViewById<TextView>(R.id.editLevel)
        private val remove = itemView.findViewById<Button>(R.id.remove)

        init {
            remove.setOnClickListener { deleteFriend(this) }
        }

        private fun deleteFriend(holder:FriendsViewHolder) {
            val position = holder.adapterPosition
            val retrofit = RetrofitClient.getInstance()
            val myService = retrofit.create(MyService::class.java)
            val str = friendsList?.get(position)
            val str1 = user?.oldUser?.getId()
            myService.apiDeleteFriend(DeleteFriend(str1,str)).enqueue(
                object:
                    Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if(response.code() != 200) {
                            Toast.makeText(itemView.context,"Response failed"+response.errorBody(), Toast.LENGTH_SHORT).show()
                            return
                        }
                        val gson = Gson()
                        val str = response.body()
                        val result = gson.fromJson(str, AddTaskResult::class.java)
                        if(result.status == "success") {
                            friendsList?.remove(friendsList?.get(position))
                            notifyDataSetChanged()
                            Toast.makeText(itemView.context,"Successfully deleted", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(itemView.context,"Response failed"+response.errorBody(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(itemView.context,"Failed to connect", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
        fun bind(friend:String) {
            //Still to implement with api call
            bindOrHide(friendName,friend)
            bindOrHide(editLevel,"1")
        }

        fun bindOrHide(textView: TextView, data:String?) {
            if(data == null) {
                textView.visibility = View.GONE
            } else {
                textView.text = data
                textView.visibility = View.VISIBLE
            }
        }
    }

}