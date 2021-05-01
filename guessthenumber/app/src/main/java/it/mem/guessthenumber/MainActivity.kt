package it.mem.guessTheNumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import it.mem.guessTheNumber.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val actionBar = supportActionBar
        actionBar!!.title = ""

        binding.ibHello.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }
}