package it.mem.myapplicationmarvel.ui.characters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import it.mem.myapplicationmarvel.databinding.ItemCharacterBinding
import it.mem.myapplicationmarvel.extensions.load
import it.mem.myapplicationmarvel.model.entity.Character

class CharactersAdapter() : PagedListAdapter<Character, CharactersAdapter.VH>(characterDiff) {

    private lateinit var binding: ItemCharacterBinding
    var onItemClick: ((Character) -> Unit)? = null
    val characters= mutableListOf<Character>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater=LayoutInflater.from(parent.context)
        binding= ItemCharacterBinding.inflate(layoutInflater, parent, false)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val character: Character? =getItem(position)
        if (character != null) {
            characters.add(position, character)
        }

        holder.txtName.text = character?.name
        holder.imgThumbnail.load("${character?.thumbnail?.path}.${character?.thumbnail?.extension}")

    }


    inner class VH(binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgThumbnail = binding.imgThumbnail
        val txtName = binding.txtName

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
}