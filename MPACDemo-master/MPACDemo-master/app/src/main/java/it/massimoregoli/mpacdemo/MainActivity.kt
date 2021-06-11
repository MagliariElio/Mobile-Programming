package it.massimoregoli.mpacdemo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Holder()

        //setting splash screen
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@MainActivity, SelectionActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }

    inner class Holder {
        init {
            //attaching the ImageView by its id and setting the splash screen image
            val ivStart: ImageView = findViewById(R.id.ivStart)
            ivStart.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.start))
        }
    }
}