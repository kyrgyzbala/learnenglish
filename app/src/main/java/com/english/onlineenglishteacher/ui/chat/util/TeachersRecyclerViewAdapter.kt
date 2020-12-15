package com.english.onlineenglishteacher.ui.chat.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.english.onlineenglishteacher.databinding.RowTeacherBinding
import com.english.onlineenglishteacher.ui.login.register.ModelUser
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class TeachersRecyclerViewAdapter(
    options: FirestoreRecyclerOptions<ModelUser>
) : FirestoreRecyclerAdapter<ModelUser, TeachersRecyclerViewAdapter.ViewHolderT>(options) {

    private var _binding: RowTeacherBinding? = null

    inner class ViewHolderT(private val binding: RowTeacherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(modelUser: ModelUser) {
            if (!modelUser.logo.isNullOrEmpty())
                Glide.with(binding.root).load(modelUser.logo).into(binding.logoImageView)
            binding.teacherName.text = modelUser.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderT {
        _binding = RowTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderT(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderT, position: Int, model: ModelUser) {
        holder.onBind(model)
    }

}