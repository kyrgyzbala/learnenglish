package com.english.onlineenglishteacher.ui.home

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.english.onlineenglishteacher.databinding.ActivityNotesBinding
import com.english.onlineenglishteacher.ui.home.util.ModelNote
import com.english.onlineenglishteacher.ui.home.util.NotesRecyclerViewAdapter
import com.english.onlineenglishteacher.util.EXTRA_LEVEL
import com.english.onlineenglishteacher.util.EXTRA_NOTE_MODEL
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class NotesActivity : AppCompatActivity(), NotesRecyclerViewAdapter.NotesClickListener {

    private lateinit var binding: ActivityNotesBinding


    private var level: ModelLevel? = null

    private var adapter: NotesRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE

        level = intent.getSerializableExtra(EXTRA_LEVEL) as ModelLevel

        binding.arrBack.setOnClickListener {
            onBackPressed()
        }

        binding.levelTextView.text = level!!.name

        initRecyclerView(level!!.code)
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView(level!!.code)
    }

    private fun initRecyclerView(code: Int) {
        binding.recyclerView.setHasFixedSize(true)
        val query = FirebaseFirestore.getInstance().collection("notes")
            .whereEqualTo("level", code)
            .orderBy("date", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<ModelNote> =
            FirestoreRecyclerOptions.Builder<ModelNote>().setQuery(query, ModelNote::class.java)
                .build()
        adapter = NotesRecyclerViewAdapter(options, this)
        binding.recyclerView.adapter = adapter
        adapter?.startListening()
    }

    override fun onClickNote(modelNote: ModelNote) {
        val intent = Intent(this, ViewNoteActivity::class.java)
        intent.putExtra(EXTRA_NOTE_MODEL, modelNote)
        startActivity(intent)
    }

}