package com.english.onlineenglishteacher.ui.chat

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.english.onlineenglishteacher.databinding.ActivityPrivateChatBinding
import com.english.onlineenglishteacher.ui.chat.util.MessagesRecyclerViewAdapter
import com.english.onlineenglishteacher.ui.chat.util.ModelMessage
import com.english.onlineenglishteacher.util.EXTRA_TEACHER_ID
import com.english.onlineenglishteacher.util.hide
import com.english.onlineenglishteacher.util.toast
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import java.util.*

class PrivateChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrivateChatBinding

    private var teacherId: String = ""

    private val db = FirebaseFirestore.getInstance()

    private var adapter: MessagesRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivateChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE

        teacherId = intent.getStringExtra(EXTRA_TEACHER_ID)!!

        binding.sendMessageButton.alpha = 0.4F
        initTeacherData()
        addListener()

        binding.arrBackPrivateChat.setOnClickListener {
            onBackPressed()
        }

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null)
            initRecyclerView(user)

        binding.sendMessageButton.setOnClickListener {
            if (binding.sendMessageButton.alpha == 1F) {
                sendNewMessage(user!!)
            }
        }

        val map = mutableMapOf<String, Any>()
        map["lastMessageRead"] = true
        val ref = db.collection("chats").document(user!!.uid + teacherId)
        ref.set(map, SetOptions.merge())

    }

    private fun sendNewMessage(user: FirebaseUser) {
        val modelMessage = ModelMessage(
            binding.messageEditText.text.toString(),
            user.uid, teacherId, user.displayName, Timestamp(Date()), false
        )
        val ref = db.collection("chats").document(user.uid + teacherId)
        ref.collection("messages").add(modelMessage).addOnSuccessListener {
            binding.messageEditText.setText("")
            binding.sendMessageButton.alpha = 0.4F

            binding.recyclerViewPrivateChat.smoothScrollToPosition(0)
        }

        val map = mutableMapOf<String, Any>()
        map["lastMessage"] = modelMessage.message
        map["lastMessageSender"] = modelMessage.sender
        map["lastMessageTime"] = Timestamp(Date())
        map["lastMessageRead"] = false
        ref.set(map, SetOptions.merge()).addOnSuccessListener {
            Log.d("dadada", "sendNewMessage: last update")
        }
    }

    private fun addListener() {
        binding.messageEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.messageEditText.text.toString().isNotEmpty())
                    binding.sendMessageButton.alpha = 1F
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    private fun initRecyclerView(user: FirebaseUser) {
        val ref = user.uid + teacherId
        val docRef = FirebaseFirestore.getInstance().collection("chats").document(ref)
        val query =
            docRef.collection("messages").orderBy("time", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<ModelMessage> =
            FirestoreRecyclerOptions.Builder<ModelMessage>()
                .setQuery(query, ModelMessage::class.java)
                .build()
        binding.recyclerViewPrivateChat.setHasFixedSize(true)

        adapter = MessagesRecyclerViewAdapter(options, ref)
        binding.recyclerViewPrivateChat.adapter = adapter

        adapter?.startListening()

        val observer = object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                binding.recyclerViewPrivateChat.smoothScrollToPosition(0)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                binding.recyclerViewPrivateChat.smoothScrollToPosition(0)
            }
        }
        adapter?.registerAdapterDataObserver(observer)

        binding.recyclerViewPrivateChat.smoothScrollToPosition(0)

        binding.recyclerViewPrivateChat.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            if (bottom < oldBottom) {
                binding.recyclerViewPrivateChat.postDelayed({
                    Log.d("InitRecyclerView", "initRecyclerView: postDelayed")
                    binding.recyclerViewPrivateChat.smoothScrollToPosition(0)
                }, 100)
            }
        }

    }


    private fun initTeacherData() {
        db.collection("teachers").document(teacherId).get().addOnSuccessListener {
            if (it.exists()) {
                val logo = it.getString("logo")
                val name = it.getString("name")
                val status = it.getString("status")
                val phoneNumber = it.getString("phoneNumber")
                if (!logo.isNullOrEmpty())
                    Glide.with(this).load(logo).into(binding.avatarPrivateChat)
                binding.userNamePrivateChat.title = name
                Log.d("NURIKO", "initTeacherData: $status")
                binding.prBarPrivateChat.hide()

                binding.callUserPrivateChat.setOnClickListener {
                    val callIntent = Intent(Intent.ACTION_DIAL)
                    callIntent.data = Uri.parse("tel:$phoneNumber")
                    startActivity(callIntent)
                }
            } else {
                toast("User does not exist!")
            }
        }
    }
}