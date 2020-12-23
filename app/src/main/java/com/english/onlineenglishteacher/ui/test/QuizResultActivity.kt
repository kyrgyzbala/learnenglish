package com.english.onlineenglishteacher.ui.test

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.english.onlineenglishteacher.databinding.ActivityQuizResultBinding
import com.english.onlineenglishteacher.util.EXTRA_COUNT
import com.english.onlineenglishteacher.util.EXTRA_QUIZ_REF
import com.english.onlineenglishteacher.util.EXTRA_QUIZ_RESULT

/**
 * @Activity
 * shows quiz result
 */

class QuizResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ref = intent.getStringExtra(EXTRA_QUIZ_REF)
        val result = intent.getIntExtra(EXTRA_QUIZ_RESULT, 0)
        val count = intent.getIntExtra(EXTRA_COUNT, 0)

        //viewing the result
        binding.textViewCount.text = count.toString()
        binding.textViewResult.text = result.toString()

        //mark passed quiz as DONE
        val edit = getSharedPreferences("QUIZ", Context.MODE_PRIVATE).edit()
        edit.putString(ref, "done")
        edit.apply()

        binding.buttonOK.setOnClickListener {
            onBackPressed()
        }
    }
}