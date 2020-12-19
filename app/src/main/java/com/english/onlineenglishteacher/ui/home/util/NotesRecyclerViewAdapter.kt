package com.english.onlineenglishteacher.ui.home.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.english.onlineenglishteacher.databinding.RowNotesBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class NotesRecyclerViewAdapter(
    options: FirestoreRecyclerOptions<ModelNote>,
    private val listener: NotesClickListener
) : FirestoreRecyclerAdapter<ModelNote, NotesRecyclerViewAdapter.ViewHolderN>(options) {


    private var _binding: RowNotesBinding? = null

    inner class ViewHolderN(private val binding: RowNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(modelNote: ModelNote) {
            binding.topicName.text = modelNote.topic

            binding.root.setOnClickListener {
                listener.onClickNote(modelNote)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderN {
        _binding = RowNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderN(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderN, position: Int, model: ModelNote) {
        holder.onBind(model)
    }

    interface NotesClickListener {
        fun onClickNote(modelNote: ModelNote)
    }
}