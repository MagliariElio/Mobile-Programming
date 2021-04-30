package it.mem.guessthenumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import it.mem.guessthenumber.databinding.ActivitySecondBinding

class Level : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEasy
        binding.btnMedium
        binding.btnDifficult

        binding.btnEasy.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            intent.putExtra("mode", 10)
            startActivity(intent)
        }

        binding.btnMedium.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            intent.putExtra("mode", 7)
            startActivity(intent)
        }

        binding.btnDifficult.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            intent.putExtra("mode", 5)
            startActivity(intent)
        }

        val actionBar = supportActionBar
        //actionBar!!.title = "Game modes"

        actionBar!!.title = getText(R.string.game_modes_title)

        actionBar.setDisplayHomeAsUpEnabled(true)

    }
}