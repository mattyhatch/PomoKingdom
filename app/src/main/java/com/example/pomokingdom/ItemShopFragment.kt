package com.example.pomokingdom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.pomokingdom.databinding.FragmentItemShopBinding


class ItemShopFragment : Fragment(R.layout.fragment_item_shop) {
    private lateinit var binding :FragmentItemShopBinding
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter2:ItemShopAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater,container,savedInstanceState)
        binding = FragmentItemShopBinding.inflate(inflater)
        recyclerView = binding.itemsList
        adapter2 = ItemShopAdapter()
        val array2:Array<String>? = arrayOf("Sword","Shield","Helmet","Boots","Wand","Axe","Hatchet","Fire Staff","Bow","Musket","Gem","Potion","fake", "lmao", "test1", "test2",
        "test3", "test4", "test5", "test6", "test7", "test8", "test9")
        adapter2.setList(array2)
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.itemsList.apply {
            adapter = adapter2
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }

        val animator1 = binding.itemsList.itemAnimator
        if (animator1 is SimpleItemAnimator) {
            animator1.supportsChangeAnimations = false
        }
    }
}