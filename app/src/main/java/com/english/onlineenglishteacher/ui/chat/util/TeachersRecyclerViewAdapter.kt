package com.english.onlineenglishteacher.ui.chat.util

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.english.onlineenglishteacher.R
import com.english.onlineenglishteacher.databinding.RowTeacherBinding
import com.english.onlineenglishteacher.ui.login.register.ModelUser
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

/**
 * RecyclerViewAdapter to handle ListOfTeachers
 */

class TeachersRecyclerViewAdapter(
    options: FirestoreRecyclerOptions<ModelUser>,
    private val listener: TeacherClickListener
) : FirestoreRecyclerAdapter<ModelUser, TeachersRecyclerViewAdapter.ViewHolderT>(options) {

    private var _binding: RowTeacherBinding? = null

    inner class ViewHolderT(private val binding: RowTeacherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(modelUser: ModelUser) {
            //setting up the view according to current ModelUser
            if (!modelUser.logo.isNullOrEmpty())
                Glide.with(binding.root).load(modelUser.logo).into(binding.logoImageView)
            binding.teacherName.text = modelUser.name
            if (modelUser.status == "online") {
                binding.statusTextView.text = binding.root.context.getString(R.string.online)
                binding.statusTextView.setTextColor(Color.parseColor("#03A9F4"))
            } else {
                binding.statusTextView.text = binding.root.context.getString(R.string.offline)
                binding.statusTextView.setTextColor(Color.parseColor("#68696A"))
            }
            binding.root.setOnClickListener {
                listener.onTeacherClick(modelUser)
            }

        }

    }

    interface TeacherClickListener {
        fun onTeacherClick(model: ModelUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderT {
        //inflating view for row of list
        _binding = RowTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderT(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderT, position: Int, model: ModelUser) {
        holder.onBind(model)
    }

}