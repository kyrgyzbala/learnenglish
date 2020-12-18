package com.english.onlineenglishteacher.ui.test.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.english.onlineenglishteacher.databinding.RowQuizzesBinding
import com.english.onlineenglishteacher.ui.test.model.ModelQuiz
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class QuizzesRecyclerViewAdapter(
    options: FirestoreRecyclerOptions<ModelQuiz>,
    private val listener: QuizClickListener
) : FirestoreRecyclerAdapter<ModelQuiz, QuizzesRecyclerViewAdapter.ViewHolderQ>(options) {

    private var _binding: RowQuizzesBinding? = null

    inner class ViewHolderQ(private val binding: RowQuizzesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(modelQuiz: ModelQuiz) {
            binding.textViewTitleTest.text = modelQuiz.name
            binding.textViewQuestionCountList.text = modelQuiz.questions.toString()

            val sp = binding.root.context.getSharedPreferences("QUIZ", Context.MODE_PRIVATE)
            val ref = snapshots.getSnapshot(adapterPosition).reference.path
            val passed = sp.getString(ref, "")

            if (!passed.isNullOrEmpty())
                binding.checkMark.visibility = View.VISIBLE
            else
                binding.checkMark.visibility = View.GONE

            binding.root.setOnClickListener {
                listener.onQuizClick(modelQuiz, adapterPosition)
            }

        }

    }

    interface QuizClickListener {
        fun onQuizClick(modelQuiz: ModelQuiz, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderQ {
        _binding = RowQuizzesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderQ(_binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolderQ, position: Int, model: ModelQuiz) {
        holder.onBind(model)
    }

}