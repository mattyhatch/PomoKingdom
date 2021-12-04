package com.example.pomokingdom.character

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pomokingdom.ApiClasses.ItemShopItem
import com.example.pomokingdom.ApiClasses.User
import com.example.pomokingdom.R

class InventoryAdapter(private var itemList:MutableList<ItemShopItem>? = null,
                       private val listener: OnItemClickListener1,
private val user: User? = null):RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.inventory_item,parent,false)
        return InventoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        holder.bind(itemList!![position])
    }

    override fun getItemCount():Int {
        return if (itemList == null) 0 else itemList!!.size
    }

    fun setList(list:MutableList<ItemShopItem>?) {
        itemList = list
        notifyDataSetChanged()
    }
    fun addToList(task:ItemShopItem) {
        itemList?.add(task)
        notifyDataSetChanged()
    }

    inner class InventoryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var itemName:TextView = itemView.findViewById(R.id.itemName)
        private var itemType:TextView = itemView.findViewById(R.id.itemType)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(task:ItemShopItem) {
            bindOrHide(task)
        }
        override fun onClick(v: View?) {
            val pos = adapterPosition
            if(pos != RecyclerView.NO_POSITION) {
                listener.onItemClick(pos)
            }
        }

        fun bindOrHide(data:ItemShopItem?) {
            if(data == null) {
                itemName.visibility = View.GONE
                itemType.visibility = View.GONE
            } else {
                itemName.text = data.getName()
                itemType.text = data.getType()
                itemName.visibility = View.VISIBLE
                itemType.visibility = View.VISIBLE
            }
        }
    }
    interface OnItemClickListener1 {
        fun onItemClick(position:Int)
    }
}