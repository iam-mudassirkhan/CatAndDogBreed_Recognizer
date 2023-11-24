package com.mudassir.breedrecognizer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler


class SplashScreen_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        val sharedPrefs = SharedPreferencesManager.getInstance(this)
        val isLoggedIn = sharedPrefs.getBoolean("isLoggedIn")

        // Start the main activity after a delay
        Handler().postDelayed({

            if (isLoggedIn){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, Onboarding_Activity::class.java))
                finish()
            }


        }, 2000)
    }
}