package com.english.onlineenglishteacher.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.english.onlineenglishteacher.R
import com.english.onlineenglishteacher.databinding.FragmentHomeBinding
import com.english.onlineenglishteacher.ui.home.util.LevelRecyclerView
import com.english.onlineenglishteacher.util.EXTRA_LEVEL
import com.english.onlineenglishteacher.util.toast


class HomeFragment : Fragment(), LevelRecyclerView.LevelClickListener {

    /**
     * Initializing ViewBinding
     */
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    /**
     * Initializing variables
     */
    private var levels = mutableListOf<ModelLevel>()
    private lateinit var adapter: LevelRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        generateLevels()
        /**
         * Creating and setting recyclerViewAdapter for recyclerViewHome
         */
        binding.recyclerViewHome.setHasFixedSize(true)
        adapter = LevelRecyclerView(this)
        binding.recyclerViewHome.adapter = adapter
        adapter.submitList(levels)
    }

    /**
     * @Function to generate list of levels
     */
    private fun generateLevels() {
        levels = mutableListOf(
            ModelLevel(1, "Beginner Level", R.drawable.absbeginner),
            ModelLevel(2, "Elementary Level", R.drawable.beginner),
            ModelLevel(3, "Pre-Intermediate Level", R.drawable.preint),
            ModelLevel(4, "Intermediate Level", R.drawable.intermadiate),
            ModelLevel(5, "Upper-Intermediate Level", R.drawable.upper),
            ModelLevel(6, "Advanced Level", R.drawable.advanced)
        )
    }

    /**
     * Function to handle level click from list
     * Opens NotesActivity and sends level as EXTRA argument for intent
     */
    override fun onLevelClick(position: Int) {
        val level = adapter.getItemAt(position)
        val intent = Intent(requireContext(), NotesActivity::class.java)
        intent.putExtra(EXTRA_LEVEL, level)
        startActivity(intent)
    }

}