package com.english.onlineenglishteacher

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.english.onlineenglishteacher.ui.login.login.LoginActivity
import com.english.onlineenglishteacher.ui.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        /**
         * Makes screen FULL
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        GlobalScope.launch(Dispatchers.Main) {
            delayFor()
        }

    }

    /**
     * Waits for 2 seconds
     * Checks If user logged in or not
     * if user was authenticated already, @MainActivity will open
     * if not @LoginActivity will open
     */

    private suspend fun delayFor() {
        delay(2000)
        val sp = getSharedPreferences("USER", Context.MODE_PRIVATE)
        val isLoggedIn = sp.getString("ISLOGGEDIN", "")
        if (isLoggedIn.isNullOrEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}