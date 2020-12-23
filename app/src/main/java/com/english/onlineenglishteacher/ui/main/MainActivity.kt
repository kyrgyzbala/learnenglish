package com.english.onlineenglishteacher.ui.main

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.english.onlineenglishteacher.R
import com.english.onlineenglishteacher.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var sectionPagerAdapter: MainPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE

        /**
         * setting pager Adapter for ViewPager2, and setting listener for BottomNavigationView
         */
        binding.navBottomView.setOnNavigationItemSelectedListener(this)
        sectionPagerAdapter = MainPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.viewPager.isUserInputEnabled = false

        viewPagerListener()
    }

    private fun viewPagerListener() {

        /**
         * Handling page/fragment change via view pager
         */

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            /**
             * Setting up fragments according to positions selected
             */
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.navBottomView.menu.findItem(R.id.home).isChecked = true
                    }
                    1 -> {
                        binding.navBottomView.menu.findItem(R.id.test).isChecked = true
                    }
                    2 -> {
                        binding.navBottomView.menu.findItem(R.id.chats).isChecked = true
                    }
                    3 -> {
                        binding.navBottomView.menu.findItem(R.id.video).isChecked = true
                    }
                    else -> {
                        binding.navBottomView.menu.findItem(R.id.profile).isChecked = true
                    }
                }
            }
        })

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        /**
         * Setting up fragments according to positions selected
         */
        binding.viewPager.currentItem = when (item.itemId) {
            R.id.home -> {
                0
            }
            R.id.test -> {
                1
            }
            R.id.chats -> {
                2
            }
            R.id.video -> {
                3
            }
            else -> {
                4
            }
        }
        return true
    }
}