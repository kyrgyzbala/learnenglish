package com.english.onlineenglishteacher.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.english.onlineenglishteacher.databinding.ActivityViewNoteBinding
import com.english.onlineenglishteacher.ui.home.util.ModelNote
import com.english.onlineenglishteacher.util.EXTRA_NOTE_MODEL

class ViewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewNoteBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE

        //getting modelNote
        val note = intent.getParcelableExtra<ModelNote>(EXTRA_NOTE_MODEL) as ModelNote

        binding.textViewTitle.text = note.topic

        //initialing web view with topic link
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(note.link)
        //setting arr back press
        binding.arrBack.setOnClickListener {
            onBackPressed()
        }
    }
}