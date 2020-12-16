package com.english.onlineenglishteacher.ui.chat.util

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.english.onlineenglishteacher.R
import com.english.onlineenglishteacher.databinding.RowMessageBinding
import com.english.onlineenglishteacher.util.getDateToday
import com.english.onlineenglishteacher.util.hide
import com.english.onlineenglishteacher.util.show
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.text.SimpleDateFormat
import java.util.*

class MessagesRecyclerViewAdapter(
    options: FirestoreRecyclerOptions<ModelMessage>,
    private val chatRef: String
) : FirestoreRecyclerAdapter<ModelMessage, MessagesRecyclerViewAdapter.ViewHolderM>(options) {

    private var _binding: RowMessageBinding? = null

    inner class ViewHolderM(private val binding: RowMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(modelMessage: ModelMessage) {
            val user = FirebaseAuth.getInstance().currentUser!!
            if (user.uid == modelMessage.sender)
                handleSelf(modelMessage)
            else
                handleReceived(modelMessage)
        }

        private fun handleReceived(modelMessage: ModelMessage) {
            binding.messageReceivedRelative.show()
            binding.messageSentRelative.hide()
            binding.textViewMessageReceived.text = modelMessage.message
            if (!modelMessage.isRead)
                updateIsRead()
            val date = getDateToShow(modelMessage.time.toDate())
            binding.timeReceivedMessage.text = date
        }

        private fun updateIsRead() {
            val snapshot = snapshots.getSnapshot(adapterPosition)
            val map = mutableMapOf<String, Any>()
            map["read"] = true
            snapshot.reference.set(map, SetOptions.merge()).addOnSuccessListener {
                val mapp = mutableMapOf<String, Any>()
                mapp["lastRead"] = true
                FirebaseFirestore.getInstance().document(chatRef).set(mapp, SetOptions.merge())
                    .addOnSuccessListener {
                        Log.d("MessagesViewHolder", "updateRead (line 108): read")
                    }
            }
        }

        private fun handleSelf(modelMessage: ModelMessage) {
            binding.messageReceivedRelative.hide()
            binding.messageSentRelative.show()
            binding.textViewMessageSent.text = modelMessage.message
            if (modelMessage.isRead)
                binding.readImgView.setImageDrawable(
                    ContextCompat.getDrawable(binding.root.context, R.drawable.ic_icon_read)
                )
            else
                binding.readImgView.setImageDrawable(
                    ContextCompat.getDrawable(binding.root.context, R.drawable.ic_icon_read_dark)
                )
            val date = getDateToShow(modelMessage.time.toDate())
            binding.timeSentMessage.text = date
        }

        private fun getDateToShow(date: Date): String {
            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT)
            val dateTimeStr = sdf.format(date)
            val timeStr = dateTimeStr.takeLast(5)
            val dateStr = getDateToday(itemView.context, dateTimeStr.take(10), 1)
            return "$dateStr $timeStr"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderM {
        _binding = RowMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderM(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderM, position: Int, model: ModelMessage) {
        holder.onBind(model)
    }

}