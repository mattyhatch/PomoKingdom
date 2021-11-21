package com.example.pomokingdom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.pomokingdom.databinding.FragmentFriendsBinding
import com.example.pomokingdom.friends.FriendsListAdapter

class FriendsFragment : Fragment(R.layout.fragment_friends) {
    private lateinit var binding :FragmentFriendsBinding
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter1:FriendsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater,container,savedInstanceState)
        binding = FragmentFriendsBinding.inflate(inflater)
        recyclerView = binding.friendsList
        adapter1 = FriendsListAdapter()
        val array:Array<String>? = arrayOf("bruhmoment","lol","lmao","1","2","3","4","5","6","7","10","lol","lmao","yellow","blue","orange","green",
        "white","bruh","moment")
        adapter1.setList(array)
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.friendsList.apply {
            adapter = adapter1
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }

        val animator = binding.friendsList.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }
}