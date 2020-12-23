package com.english.onlineenglishteacher.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.english.onlineenglishteacher.ui.chat.ChatFragment
import com.english.onlineenglishteacher.ui.home.HomeFragment
import com.english.onlineenglishteacher.ui.profile.ProfileFragment
import com.english.onlineenglishteacher.ui.test.TestFragment
import com.english.onlineenglishteacher.ui.video.VideoFragment

class MainPagerAdapter (fm: FragmentActivity) :
    FragmentStateAdapter(fm) {

    /**
     * returning fragment count
     */
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {

        /**
         * return fragment according to position selected
         */
        return when (position) {
            0 -> HomeFragment()
            1 -> TestFragment()
            2 -> ChatFragment()
            3 -> VideoFragment()
            else -> ProfileFragment()
        }
    }
}