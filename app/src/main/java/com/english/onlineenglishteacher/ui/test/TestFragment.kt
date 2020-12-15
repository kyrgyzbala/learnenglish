package com.english.onlineenglishteacher.ui.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.english.onlineenglishteacher.R
import com.english.onlineenglishteacher.databinding.FragmentTestBinding
import com.english.onlineenglishteacher.ui.home.ModelLevel
import com.english.onlineenglishteacher.ui.home.util.LevelRecyclerView
import com.english.onlineenglishteacher.util.toast


class TestFragment : Fragment(), LevelRecyclerView.LevelClickListener {

    private var _binding: FragmentTestBinding? = null
    private val binding: FragmentTestBinding get() = _binding!!

    private var levels = mutableListOf<ModelLevel>()

    private lateinit var adapter: LevelRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        generateLevels()
        binding.recyclerViewQuiz.setHasFixedSize(true)
        adapter = LevelRecyclerView(this)
        binding.recyclerViewQuiz.adapter = adapter
        adapter.submitList(levels)
    }

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

    override fun onLevelClick(position: Int) {
        requireActivity().toast("clicked $position")
    }

}