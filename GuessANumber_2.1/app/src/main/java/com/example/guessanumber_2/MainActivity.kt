package com.example.guessanumber_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.guessanumber_2.databinding.ActivitySplashScreenBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var fishAnimation: Animation
    private lateinit var titleAnimation:Animation
    private lateinit var msgAnimation:Animation
    companion object {
        private var SPLASHSCREEN: Long = 4500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        fishAnimation = AnimationUtils.loadAnimation(this, R.anim.fish_animation)
        titleAnimation = AnimationUtils.loadAnimation(this, R.anim.title_anim)
        msgAnimation = AnimationUtils.loadAnimation(this, R.anim.msg_anim)
        binding.imageView?.animation = fishAnimation
        binding.tvTitle?.animation = titleAnimation
        binding.tvHi?.animation = msgAnimation
        binding.textView3?.animation = msgAnimation

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASHSCREEN)


    }

}