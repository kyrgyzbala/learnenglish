package com.english.onlineenglishteacher.ui.test

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.english.onlineenglishteacher.R
import com.english.onlineenglishteacher.databinding.ActivityQuizActualBinding
import com.english.onlineenglishteacher.ui.test.model.ModelQ
import com.english.onlineenglishteacher.ui.test.model.ModelQuestion
import com.english.onlineenglishteacher.ui.test.model.ModelQuiz
import com.english.onlineenglishteacher.util.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class QuizActualActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizActualBinding

    private var refPath: String? = ""
    private val questions = mutableListOf<ModelQ>()

    private var index = 0

    private var progressRange = 1
    private var resultScore: Int = 0

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
            } else {
                val intent = Intent(this, QuizResultActivity::class.java)
                intent.putExtra(EXTRA_QUIZ_REF, refPath)
                intent.putExtra(EXTRA_QUIZ_RESULT, resultScore)
                intent.putExtra(EXTRA_COUNT, quiz.questions)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                finish()
                startActivity(intent)
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
        val textB = "B) ${questions[index].varB}"
        val textC = "C) ${questions[index].varC}"
        val textD = "D) ${questions[index].varD}"

        binding.buttonVariantA.text = textA
        binding.buttonVariantB.text = textB
        binding.buttonVariantC.text = textC
        binding.buttonVariantD.text = textD

        if (questions[index].isAnswered != -1) {
            if (questions[index].isAnswered == questions[index].answer) {
                //Answered correctly
                showCorrectAnswer()
            } else {
                //Answer was wrong
                showCorrectAnswer()
                when (questions[index].isAnswered) {
                    1 -> {
                        binding.buttonVariantA.setTextColor(Color.RED)
                        binding.buttonVariantA.setBackgroundResource(R.drawable.back_answer_false)
                    }
                    2 -> {
                        binding.buttonVariantB.setTextColor(Color.RED)
                        binding.buttonVariantB.setBackgroundResource(R.drawable.back_answer_false)
                    }
                    3 -> {
                        binding.buttonVariantC.setTextColor(Color.RED)
                        binding.buttonVariantC.setBackgroundResource(R.drawable.back_answer_false)
                    }
                    else -> {
                        binding.buttonVariantD.setTextColor(Color.RED)
                        binding.buttonVariantD.setBackgroundResource(R.drawable.back_answer_false)
                    }
                }
            }
        } else {

            binding.buttonVariantA.setBackgroundColor(Color.WHITE)
            binding.buttonVariantA.setTextColor(Color.parseColor("#12202E"))

            binding.buttonVariantB.setBackgroundColor(Color.WHITE)
            binding.buttonVariantB.setTextColor(Color.parseColor("#12202E"))

            binding.buttonVariantC.setBackgroundColor(Color.WHITE)
            binding.buttonVariantC.setTextColor(Color.parseColor("#12202E"))

            binding.buttonVariantD.setBackgroundColor(Color.WHITE)
            binding.buttonVariantD.setTextColor(Color.parseColor("#12202E"))

        }

    }

    private fun showCorrectAnswer() {
        when (questions[index].answer) {
            1 -> {
                binding.buttonVariantA.setBackgroundResource(R.drawable.back_answer_true)
                binding.buttonVariantA.setTextColor(Color.WHITE)

                binding.buttonVariantB.setBackgroundColor(Color.WHITE)
                binding.buttonVariantB.setTextColor(Color.parseColor("#12202E"))

                binding.buttonVariantC.setBackgroundColor(Color.WHITE)
                binding.buttonVariantC.setTextColor(Color.parseColor("#12202E"))

                binding.buttonVariantD.setBackgroundColor(Color.WHITE)
                binding.buttonVariantD.setTextColor(Color.parseColor("#12202E"))
            }
            2 -> {

                binding.buttonVariantB.setBackgroundResource(R.drawable.back_answer_true)
                binding.buttonVariantB.setTextColor(Color.WHITE)

                binding.buttonVariantA.setBackgroundColor(Color.WHITE)
                binding.buttonVariantA.setTextColor(Color.parseColor("#12202E"))

                binding.buttonVariantC.setBackgroundColor(Color.WHITE)
                binding.buttonVariantC.setTextColor(Color.parseColor("#12202E"))

                binding.buttonVariantD.setBackgroundColor(Color.WHITE)
                binding.buttonVariantD.setTextColor(Color.parseColor("#12202E"))

            }
            3 -> {
                binding.buttonVariantC.setBackgroundResource(R.drawable.back_answer_true)
                binding.buttonVariantC.setTextColor(Color.WHITE)

                binding.buttonVariantB.setBackgroundColor(Color.WHITE)
                binding.buttonVariantB.setTextColor(Color.parseColor("#12202E"))

                binding.buttonVariantA.setBackgroundColor(Color.WHITE)
                binding.buttonVariantA.setTextColor(Color.parseColor("#12202E"))

                binding.buttonVariantD.setBackgroundColor(Color.WHITE)
                binding.buttonVariantD.setTextColor(Color.parseColor("#12202E"))
            }
            else -> {
                binding.buttonVariantD.setBackgroundResource(R.drawable.back_answer_true)
                binding.buttonVariantD.setTextColor(Color.WHITE)

                binding.buttonVariantB.setBackgroundColor(Color.WHITE)
                binding.buttonVariantB.setTextColor(Color.parseColor("#12202E"))

                binding.buttonVariantC.setBackgroundColor(Color.WHITE)
                binding.buttonVariantC.setTextColor(Color.parseColor("#12202E"))

                binding.buttonVariantA.setBackgroundColor(Color.WHITE)
                binding.buttonVariantA.setTextColor(Color.parseColor("#12202E"))
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
                    addAnswerListeners()
                }
            }
    }

    private fun addAnswerListeners() {
        binding.buttonVariantA.setOnClickListener {
            if (questions[index].isAnswered == -1) {
                questions[index].isAnswered = 1
                if (questions[index].answer == 1) {
                    resultScore++
                    showCorrectAnswer()
                } else {
                    showCorrectAnswer()
                    binding.buttonVariantA.setTextColor(Color.RED)
                    binding.buttonVariantA.setBackgroundResource(R.drawable.back_answer_false)
                }
            }
        }

        binding.buttonVariantB.setOnClickListener {
            if (questions[index].isAnswered == -1) {
                questions[index].isAnswered = 2
                if (questions[index].answer == 2) {
                    resultScore++
                    showCorrectAnswer()
                } else {
                    showCorrectAnswer()
                    binding.buttonVariantB.setTextColor(Color.RED)
                    binding.buttonVariantB.setBackgroundResource(R.drawable.back_answer_false)
                }
            }
        }

        binding.buttonVariantC.setOnClickListener {
            if (questions[index].isAnswered == -1) {
                questions[index].isAnswered = 3
                if (questions[index].answer == 3) {
                    resultScore++
                    showCorrectAnswer()
                } else {
                    showCorrectAnswer()
                    binding.buttonVariantC.setTextColor(Color.RED)
                    binding.buttonVariantC.setBackgroundResource(R.drawable.back_answer_false)
                }
            }
        }

        binding.buttonVariantD.setOnClickListener {
            if (questions[index].isAnswered == -1) {
                questions[index].isAnswered = 4
                if (questions[index].answer == 4) {
                    resultScore++
                    showCorrectAnswer()
                } else {
                    showCorrectAnswer()
                    binding.buttonVariantD.setTextColor(Color.RED)
                    binding.buttonVariantD.setBackgroundResource(R.drawable.back_answer_false)
                }
            }
        }
    }
}