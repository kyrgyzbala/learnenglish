package com.english.onlineenglishteacher.ui.chat.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.english.onlineenglishteacher.ui.chat.ChatListFragment
import com.english.onlineenglishteacher.ui.chat.TeachersFragment

class ChatPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ChatListFragment()
            else -> TeachersFragment()
        }
    }
}