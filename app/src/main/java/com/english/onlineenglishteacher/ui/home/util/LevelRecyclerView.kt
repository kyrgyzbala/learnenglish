package com.english.onlineenglishteacher.ui.home.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.english.onlineenglishteacher.databinding.RowLevelsBinding
import com.english.onlineenglishteacher.ui.home.ModelLevel

class LevelRecyclerView(
    private val listener: LevelClickListener
) : ListAdapter<ModelLevel, LevelRecyclerView.ViewHolderL>(DIFF) {

    private var _binding: RowLevelsBinding? = null

    fun getItemAt(position: Int): ModelLevel {
        return getItem(position)
    }

    inner class ViewHolderL(private val binding: RowLevelsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val current = getItemAt(position)
            binding.textView.text = current.name
            binding.imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    current.icon
                )
            )

            binding.root.setOnClickListener {
                listener.onLevelClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderL {
        _binding = RowLevelsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderL(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderL, position: Int) {
        holder.onBind(position)
    }

    interface LevelClickListener {
        fun onLevelClick(position: Int)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<ModelLevel>() {
            override fun areItemsTheSame(oldItem: ModelLevel, newItem: ModelLevel): Boolean {
                return oldItem.code == newItem.code
            }

            override fun areContentsTheSame(oldItem: ModelLevel, newItem: ModelLevel): Boolean {
                return oldItem.icon == newItem.icon
            }

        }
    }

}