package com.example.guessanumber_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.guessanumber_2.databinding.ActivityMainBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imgBtnSecondActivity = findViewById<ImageButton>(R.id.ib_Hello)

        val actionBar = supportActionBar
        actionBar!!.title = ""



        imgBtnSecondActivity.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }
}