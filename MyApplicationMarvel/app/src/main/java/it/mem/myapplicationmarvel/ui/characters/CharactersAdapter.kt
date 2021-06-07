package it.mem.myapplicationmarvel.ui.characters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import it.mem.myapplicationmarvel.data.local.FavoriteCharacters
import it.mem.myapplicationmarvel.data.local.FavoriteCharactersDao
import it.mem.myapplicationmarvel.data.local.FavoriteCharactersDatabase
import it.mem.myapplicationmarvel.databinding.ItemCharacterBinding
import it.mem.myapplicationmarvel.extensions.load
import it.mem.myapplicationmarvel.data.model.entity.Character
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersAdapter : PagedListAdapter<Character, CharactersAdapter.VH>(characterDiff) {

    private lateinit var binding: ItemCharacterBinding
    var onItemClick: ((Character) -> Unit)? = null
    val characters= mutableListOf<Character>()

    private var userDb: FavoriteCharactersDatabase? = FavoriteCharactersDatabase.INSTANCE
    private var characterDao: FavoriteCharactersDao?=userDb?.FavoriteCharactersDao()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater=LayoutInflater.from(parent.context)
        binding= ItemCharacterBinding.inflate(layoutInflater, parent, false)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)



        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val character: Character? =getItem(position)
        var isChecked= false
        if(character!=null) {
            characters.add(position, character)
            holder.txtName.text = character.name
            holder.imgThumbnail.load("${character.thumbnail.path}/landscape_amazing.${character.thumbnail.extension}")
            //holder.ckFavorite.isChecked = true


            CoroutineScope(Dispatchers.IO).launch {
                val count = checkCharacter(character.id)
                //withContext(Dispatchers.Main) {
                    if (count != null) {
                        if (count > 0) {
                            isChecked = true
                            Log.v("Marvel", " ${character.id} count $count")
                        } else {
                            isChecked = false
                            Log.v("Marvel", "count $count")
                        }
                    }


                //}

                holder.ckFavorite.isChecked = isChecked
            }
        }


        holder.ckFavorite.setOnClickListener {
            isChecked=!isChecked
            if (isChecked){
                character?.name?.let { it1 -> addToFavorite(it1, character.id,"${character.thumbnail.path}/landscape_amazing.${character.thumbnail.extension}") }
            }else{
                character?.id?.let { it1 -> removeFromFavorite(it1, character.name, "${character.thumbnail.path}/landscape_amazing.${character.thumbnail.extension}") }
            }
        }

        holder.ckFavorite.isChecked=isChecked

    }


    inner class VH(binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgThumbnail = binding.imgThumbnail
        val txtName = binding.txtName
        val ckFavorite = binding.ckFavorite

        init {
            binding.root.setOnClickListener {

                onItemClick?.invoke(characters[adapterPosition])
            }

        }

    }

    /*interface onCharacterClickListener {
        fun onCharacterClick(position: Int)
    }*/

    companion object {
        val characterDiff = object: DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(old: Character, new: Character): Boolean {
                return old.id == new.id

            }

            override fun areContentsTheSame(old: Character, new: Character): Boolean {
                return old == new
            }

        }
    }

    private fun addToFavorite(name:String, id:Int, image:String){
        CoroutineScope(Dispatchers.IO).launch {
            val character = FavoriteCharacters(
                id,
                name,
                image
            )
            characterDao?.addCharacter(character)
        }
        Log.w("Marvel", "$name, $id aggiunto")
    }

    private fun checkCharacter(id:Int)= characterDao?.checkCharacter(id)

    private fun removeFromFavorite(id: Int, name: String, image:String){
        CoroutineScope(Dispatchers.IO).launch {
            val favoriteCharacter= FavoriteCharacters(id, name, image)
            characterDao?.removeFromFavorite(favoriteCharacter)
        }
        Log.w("Marvel", "$name, $id rimosso")

    }


}