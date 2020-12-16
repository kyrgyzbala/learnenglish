package com.english.onlineenglishteacher.ui.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.english.onlineenglishteacher.R
import com.english.onlineenglishteacher.databinding.FragmentTeachersBinding
import com.english.onlineenglishteacher.ui.chat.util.ModelChat
import com.english.onlineenglishteacher.ui.chat.util.TeachersRecyclerViewAdapter
import com.english.onlineenglishteacher.ui.login.register.ModelUser
import com.english.onlineenglishteacher.util.EXTRA_TEACHER_ID
import com.english.onlineenglishteacher.util.toast
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*


class TeachersFragment : Fragment(), TeachersRecyclerViewAdapter.TeacherClickListener {

    private var _binding: FragmentTeachersBinding? = null
    private val binding: FragmentTeachersBinding get() = _binding!!

    private var adapter: TeachersRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeachersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

    }

    private fun initRecyclerView() {
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("teachers")
        val query = ref.orderBy("time", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<ModelUser> =
            FirestoreRecyclerOptions.Builder<ModelUser>().setQuery(query, ModelUser::class.java)
                .build()
        adapter = TeachersRecyclerViewAdapter(options, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.visibility = View.VISIBLE
        adapter?.startListening()
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    override fun onTeacherClick(model: ModelUser) {
        val user = FirebaseAuth.getInstance().currentUser!!
        val reff = user.uid + model.uid
        Log.d("NURIKO", "onTeacherClick: $reff")
        val ref = FirebaseFirestore.getInstance().collection("chats").document(reff)
        ref.get().addOnCompleteListener {
            if (it.isSuccessful) {
                if (it.result.exists()) {
                    goToChat(model.uid)
                } else {
                    createNewChat(ref, user.uid, model.uid)
                }
            } else {
                createNewChat(ref, user.uid, model.uid)
            }
        }
    }

    private fun createNewChat(ref: DocumentReference, userId: String, teacherId: String?) {
        val modelChat = ModelChat(
            userId, teacherId, "Chat created", userId, Timestamp(Date()), false
        )
        Log.d("NURIKO", "createNewChat: ${ref.path}")
        ref.set(modelChat).addOnCompleteListener {
            if (it.isSuccessful) {
                goToChat(teacherId)
            } else {
                requireActivity().toast(getString(R.string.errorTryAgain))
            }
        }
    }

    private fun goToChat(uid: String?) {
        Log.d("NURIKO", "goToChat: $uid")
        val intent = Intent(requireContext(), PrivateChatActivity::class.java)
        intent.putExtra(EXTRA_TEACHER_ID, uid)
        startActivity(intent)
    }


}