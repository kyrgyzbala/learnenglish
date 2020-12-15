package com.english.onlineenglishteacher.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.english.onlineenglishteacher.R
import com.english.onlineenglishteacher.databinding.FragmentTeachersBinding
import com.english.onlineenglishteacher.ui.chat.util.TeachersRecyclerViewAdapter
import com.english.onlineenglishteacher.ui.login.register.ModelUser
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class TeachersFragment : Fragment() {

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

        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("teachers")
        val query = ref.orderBy("time", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<ModelUser> =
            FirestoreRecyclerOptions.Builder<ModelUser>().setQuery(query, ModelUser::class.java)
                .build()
        adapter = TeachersRecyclerViewAdapter(options)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.visibility = View.VISIBLE
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }


}