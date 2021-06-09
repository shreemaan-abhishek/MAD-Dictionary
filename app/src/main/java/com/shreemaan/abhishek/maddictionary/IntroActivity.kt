package com.shreemaan.abhishek.maddictionary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView

@Suppress("DEPRECATION")
class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        //hide status bar and make our splash as full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //binding appName, image and lottie json file
        val splash:ImageView = findViewById(R.id.img)
        val lottieAnimationView: LottieAnimationView = findViewById(R.id.lottie)
        val appName: TextView = findViewById(R.id.appName)

        // animating appName, bg and lottie json file
        splash.animate().translationY(-4000f).setDuration(1600).startDelay = 2000
        appName.animate().translationY(-4000f).setDuration(1000).startDelay = 2000
        lottieAnimationView.animate().translationY(1400f).setDuration(1000).startDelay = 2000

        // using postDelayed(Runnable, time) method to send a message with a delayed time.
        Handler().postDelayed({
            val intent = Intent(this, DictionaryActivity::class.java)
            startActivity(intent)
            finish()
        }, 3500) // 3500 -> miliseconds => 3.1 secs
    }
}