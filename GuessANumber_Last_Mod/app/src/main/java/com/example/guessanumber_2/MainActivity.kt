package com.example.guessanumber_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.guessanumber_2.databinding.ActivitySplashScreenBinding

//@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var fishAnimation: Animation
    private lateinit var titleAnimation:Animation
    private lateinit var msgAnimation:Animation
    private lateinit var bubble1Animation:Animation
    private lateinit var bubble2Animation:Animation
    private lateinit var bubble3Animation:Animation
    companion object {
        private var SPLASHSCREEN: Long = 5000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        fishAnimation = AnimationUtils.loadAnimation(this, R.anim.fish_anim)
        titleAnimation = AnimationUtils.loadAnimation(this, R.anim.title_anim)
        msgAnimation = AnimationUtils.loadAnimation(this, R.anim.msg_anim)
        bubble1Animation = AnimationUtils.loadAnimation(this, R.anim.bubble1_anim)
        bubble2Animation = AnimationUtils.loadAnimation(this, R.anim.bubble2_anim)
        bubble3Animation = AnimationUtils.loadAnimation(this, R.anim.bubble3_anim)

        binding.ivHello.animation = fishAnimation
        binding.ivTitle.animation = titleAnimation
        binding.ivGreet.animation = msgAnimation
        binding.ivBubble1.animation = bubble1Animation
        binding.ivBubble2.animation = bubble2Animation
        binding.ivBubble3.animation = bubble3Animation

        /*window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )*/

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASHSCREEN)


    }

}