package com.english.onlineenglishteacher.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.english.onlineenglishteacher.R
import com.english.onlineenglishteacher.databinding.FragmentChatBinding
import com.english.onlineenglishteacher.ui.chat.util.ChatPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding: FragmentChatBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabs = listOf(
            getString(R.string.chat),
            getString(R.string.teachers)
        )

        binding.tabsMainChat.tabMode = TabLayout.MODE_SCROLLABLE

        val adapterChat = ChatPagerAdapter(this.requireActivity())

        binding.viewPagerMainChats.adapter = adapterChat
        TabLayoutMediator(binding.tabsMainChat, binding.viewPagerMainChats) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }

}