package it.mem.myapplicationmarvel.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import it.mem.myapplicationmarvel.databinding.ActivityCharacterDetailsBinding
import it.mem.myapplicationmarvel.extensions.load
import it.mem.myapplicationmarvel.model.api.MarvelAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharacterDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras=intent.extras
        val id=extras?.getInt("characterId", 0)
        Log.v("MarvelCharId", id.toString())
        if (id != null) {
            CoroutineScope(Dispatchers.IO).launch {

                val responseCharacters = MarvelAPI.getService().searchCaracterById(id)
                launch(Dispatchers.Main){
                    val character = responseCharacters.data.results[0]
                    binding.ivCharacter.load("${character.thumbnail.path}.${character.thumbnail.extension}")
                    binding.txtDescription.text=character.description
                    binding.txtName.text=character.name
                    binding.txtId.text= character.id.toString()
                    Log.v("MarvelUrl", "${character.thumbnail.path}/standard_medium.${character.thumbnail.extension}")

                }
            }
        }
    }

}