package com.english.onlineenglishteacher.ui.test

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.english.onlineenglishteacher.databinding.ActivityQuizByLevelBinding
import com.english.onlineenglishteacher.ui.home.ModelLevel
import com.english.onlineenglishteacher.ui.test.model.ModelQuiz
import com.english.onlineenglishteacher.ui.test.util.QuizzesRecyclerViewAdapter
import com.english.onlineenglishteacher.util.EXTRA_LEVEL
import com.english.onlineenglishteacher.util.EXTRA_QUIZ_MODEL
import com.english.onlineenglishteacher.util.EXTRA_QUIZ_REF
import com.english.onlineenglishteacher.util.toast
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class QuizByLevelActivity : AppCompatActivity(), QuizzesRecyclerViewAdapter.QuizClickListener {

    private lateinit var binding: ActivityQuizByLevelBinding

    private var adapter: QuizzesRecyclerViewAdapter? = null

    private var level: ModelLevel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizByLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE

        level = intent.getSerializableExtra(EXTRA_LEVEL) as ModelLevel

        binding.arrBackQuiz.setOnClickListener {
            onBackPressed()
        }

        binding.textViewTitle.text = level!!.name

        initRecyclerView(level!!.code)
    }

    override fun onResume() {
        super.onResume()
        if (level != null)
            initRecyclerView(level!!.code)
    }

    private fun initRecyclerView(code: Int) {
        binding.recyclerView.setHasFixedSize(true)

        val query = FirebaseFirestore.getInstance().collection("quizzes")
            .whereEqualTo("level", code)
            .orderBy("date", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<ModelQuiz> =
            FirestoreRecyclerOptions.Builder<ModelQuiz>().setQuery(query, ModelQuiz::class.java)
                .build()
        adapter = QuizzesRecyclerViewAdapter(options, this)
        binding.recyclerView.adapter = adapter

        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    override fun onQuizClick(modelQuiz: ModelQuiz, position: Int) {
        toast("Clicked ${modelQuiz.name}")
        val ref = adapter!!.snapshots.getSnapshot(position).reference.path
        val intent = Intent(this, QuizActualActivity::class.java)
        intent.putExtra(EXTRA_QUIZ_REF, ref)
        intent.putExtra(EXTRA_QUIZ_MODEL, modelQuiz)
        startActivity(intent)
    }
}