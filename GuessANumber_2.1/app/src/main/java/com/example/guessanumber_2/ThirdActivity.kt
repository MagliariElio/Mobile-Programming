package com.example.guessanumber_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.guessanumber_2.databinding.ActivitySecondBinding
import android.os.Bundle

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEasy
        binding.btnMedium
        binding.btnDifficult

        binding.btnEasy.setOnClickListener {
            val intent = Intent(this, FourthActivity::class.java)
            startActivity(intent)
        }

        binding.btnMedium.setOnClickListener {
            val intent = Intent(this, FourthActivity::class.java)
            startActivity(intent)
        }

        binding.btnDifficult.setOnClickListener {
            val intent = Intent(this, FourthActivity::class.java)
            startActivity(intent)
        }

        val actionBar = supportActionBar
        //actionBar!!.title = "Game modes"

        actionBar!!.title = getText(R.string.game_modes_title)

        actionBar.setDisplayHomeAsUpEnabled(true)

    }
}