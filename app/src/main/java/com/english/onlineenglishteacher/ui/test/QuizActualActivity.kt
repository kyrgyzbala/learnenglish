package com.english.onlineenglishteacher.ui.test

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.english.onlineenglishteacher.databinding.ActivityQuizActualBinding
import com.english.onlineenglishteacher.ui.test.model.ModelQ
import com.english.onlineenglishteacher.ui.test.model.ModelQuestion
import com.english.onlineenglishteacher.ui.test.model.ModelQuiz
import com.english.onlineenglishteacher.util.EXTRA_QUIZ_MODEL
import com.english.onlineenglishteacher.util.EXTRA_QUIZ_REF
import com.english.onlineenglishteacher.util.hide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class QuizActualActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizActualBinding

    private var refPath: String? = ""
    private val questions = mutableListOf<ModelQ>()

    private var index = 0

    private var progressRange = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizActualBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.WHITE
        val quiz = intent.getParcelableExtra<ModelQuiz>(EXTRA_QUIZ_MODEL) as ModelQuiz
        refPath = intent.getStringExtra(EXTRA_QUIZ_REF)
        binding.textViewTitle.text = quiz.name

        progressRange = 100 / quiz.questions
        binding.progressBarTest.progress = progressRange
        loadQuestions()
        binding.questionCount.text = quiz.questions.toString()

        binding.nextTest.setOnClickListener {
            if (index < (quiz.questions - 1)) {
                index++
                binding.progressBarTest.progress += progressRange
                initUI()
            }
        }

        binding.prevTest.setOnClickListener {
            if (index > 0) {
                index--
                binding.progressBarTest.progress -= progressRange
                initUI()
            }
        }
    }

    private fun initUI() {
        if (index == 0)
            binding.prevTest.visibility = View.GONE
        else
            binding.prevTest.visibility = View.VISIBLE
        binding.questionTextViewTest.text = questions[index].question

        binding.testPassed.text = (index + 1).toString()

        val textA = "A) ${questions[index].varA}"
        val textB = "A) ${questions[index].varB}"
        val textC = "A) ${questions[index].varC}"
        val textD = "A) ${questions[index].varD}"

        binding.buttonVariantA.text = textA
        binding.buttonVariantB.text = textB
        binding.buttonVariantC.text = textC
        binding.buttonVariantD.text = textD

        if (questions[index].isAnswered != -1) {
            if (questions[index].isAnswered == questions[index].answer) {

            }
        }

    }

    private fun loadQuestions() {
        FirebaseFirestore.getInstance().document(refPath!!).collection("questions").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result
                    for (s in result) {
                        val question = s.getString("question")
                        val varA = s.getString("varA")
                        val varB = s.getString("varB")
                        val varC = s.getString("varC")
                        val varD = s.getString("varD")
                        val answer = s.getLong("answer")?.toInt()
                        val q = ModelQ(question, varA, varB, varC, varD, answer!!)
                        questions.add(q)
                    }
                    binding.progressBar.hide()
                    initUI()
                }
            }
    }
}