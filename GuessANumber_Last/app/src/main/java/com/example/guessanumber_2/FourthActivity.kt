package com.example.guessanumber_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isInvisible
import com.example.guessanumber_2.databinding.ActivityThirdBinding

class FourthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private var maxAttempts = 10 // easy 10, medium 7, difficult 5
    var textToast = ""

    private companion object{
        var maxValue = 100
    }

    lateinit var gan: GAN
    var gameStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)    //da 25 a 27 per mostrare interfaccia

        val actionBar = supportActionBar
        actionBar!!.title = ""

        actionBar.setDisplayHomeAsUpEnabled(true)

        maxAttempts=intent.getIntExtra("mode", 10)
        gan=GAN(maxAttempts, maxValue)
        
        var bool=true
        if (savedInstanceState != null){
            bool = savedInstanceState.getBoolean("value")
        }
        Holder(binding, bool)
    }
    inner class Holder(binding: ActivityThirdBinding, bool: Boolean) { //classe di View gestire parte di view e controllare tocchi su oggetti
        private var state = 0
        private val mybinding = binding
        private val btns = listOf(
            mybinding.btn0,
            mybinding.btn1,
            mybinding.btn2,
            mybinding.btn3,
            mybinding.btn4,
            mybinding.btn5,
            mybinding.btn6,
            mybinding.btn7,
            mybinding.btn8,
            mybinding.btn9
        )

        init {
            for (btn in btns) {
                btn.setOnClickListener(NumberClick())
            }
            mybinding.btnCanc.setOnClickListener(ActionClick())
            mybinding.btnOK.setOnClickListener(ActionClick())
            mybinding.ivState.setOnClickListener{
                startGame()
            }
            if(bool) {
                disableButtons()
                mybinding.tvAttempts.isInvisible = true
                textToast = getText(R.string.tap_face_start_toast) as String
                Toast.makeText(this@FourthActivity, textToast, Toast.LENGTH_LONG).show()
            }
        }

        private fun disableButtons(){
            for(btn in btns){
                btn.isEnabled= false
            }
            mybinding.btnOK.isEnabled=false
            mybinding.btnCanc.isEnabled=false
        }

        private fun enableButtons(){
            for(btn in btns){
                btn.isEnabled= true
            }
            mybinding.btnOK.isEnabled=true
            mybinding.btnCanc.isEnabled=true
        }

        private fun startGame(){
            gan = GAN(maxAttempts, maxValue)
            mybinding.tvGAN.text=""
            mybinding.tvAttempts.text="0"
            mybinding.tvAttempts.isInvisible=false
            gameStarted = true
            gan.new()
            mybinding.ivState.setImageResource(R.drawable.wait)
            enableButtons()
        }

        fun check(): GAN.Answer{
            val answer = gan.check(mybinding.tvGAN.text.toString().toInt())
            state = when(answer){
                GAN.Answer.YOULOOSE->{gan.new()
                    gameStarted = false
                    R.drawable.you_loose
                }
                GAN.Answer.YOUWIN->{gan.new()
                    gameStarted = false
                    R.drawable.you_win
                }
                GAN.Answer.TOOSMALL-> {
                    textToast = getText(R.string.too_low) as String
                    Toast.makeText(this@FourthActivity, textToast, Toast.LENGTH_SHORT).show()
                    R.drawable.wrong
                }

                GAN.Answer.TOOBIG-> {
                    textToast = getText(R.string.too_high) as String
                    Toast.makeText(this@FourthActivity,textToast, Toast.LENGTH_SHORT).show()
                    R.drawable.wrong
                }
            }
            mybinding.ivState.setImageResource(state)
            mybinding.ivState.tag=("android.resource://com.example.guessanumber/$state")
            return answer
        }

        inner class NumberClick : View.OnClickListener {
            override fun onClick(view: View?) {
                view as Button
                val txt = mybinding.tvGAN.text.toString() + view.text.toString()
                mybinding.tvGAN.text = txt
                if(txt.toInt() !in 1..maxValue){
                    mybinding.btnOK.isEnabled = false
                    textToast = getText(R.string.number_advice) as String + maxValue.toString()
                    Toast.makeText(this@FourthActivity, textToast, Toast.LENGTH_SHORT).show()
                }else{
                    mybinding.btnOK.isEnabled = true
                }
            }
        }

        inner class ActionClick :View.OnClickListener{
            override fun onClick(view: View?) {
                view as Button
                when(view.id){
                    R.id.btnCanc -> {mybinding.tvGAN.text = mybinding.tvGAN.text.toString().dropLast(1)
                        mybinding.btnOK.isEnabled = mybinding.tvGAN.text.toString()!=""}
                    R.id.btnOK -> {when(check()) {
                        GAN.Answer.TOOBIG, GAN.Answer.TOOSMALL -> mybinding.tvAttempts.text = gan.attempts.toString()
                        GAN.Answer.YOUWIN, GAN.Answer.YOULOOSE -> {
                            mybinding.tvAttempts.text = (mybinding.tvAttempts.text.toString().toInt()+1).toString()
                            disableButtons()
                            textToast = getText(R.string.tap_face_restart_toast) as String
                            Toast.makeText(this@FourthActivity,textToast,Toast.LENGTH_LONG).show()
                        }
                    }
                        mybinding.tvGAN.text=""
                        mybinding.btnOK.isEnabled = false

                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString("GAN", binding.tvGAN.text.toString())
        savedInstanceState.putString("attempts", binding.tvAttempts.text.toString())
        savedInstanceState.putString("image", binding.ivState.tag.toString())
        savedInstanceState.putIntArray("state", gan.getState())
        savedInstanceState.putBoolean("value", false)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.tvGAN.text = savedInstanceState.getString("GAN")
        binding.tvAttempts.text = savedInstanceState.getString("attempts")
        binding.ivState.setImageURI(savedInstanceState.getString("image")?.toUri())
        savedInstanceState.getIntArray("state")?.let { gan.setState(it) }
    }
}




