package it.mem.myapplicationmarvel.data.model.paging.characters.characterdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import it.mem.myapplicationmarvel.databinding.ItemComicsBinding
import it.mem.myapplicationmarvel.extensions.load
import it.mem.myapplicationmarvel.data.model.entity.Comics

class StoriesByCharactersAdapter() : PagedListAdapter<Comics, StoriesByCharactersAdapter.VH>(
    characterDiff
) {

    private lateinit var binding: ItemComicsBinding
    var onItemClick: ((Comics) -> Unit)? = null
    val comics= mutableListOf<Comics>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater=LayoutInflater.from(parent.context)
        binding= ItemComicsBinding.inflate(layoutInflater, parent, false)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val comic: Comics? =getItem(position)
        if (comic != null) {
            comics.add(position, comic)
        }

        holder.txtName.text = comic?.title
        holder.imgThumbnail.load("${comic?.thumbnail?.path}.${comic?.thumbnail?.extension}")

    }


    inner class VH(binding: ItemComicsBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgThumbnail = binding.imgThumbnail
        val txtName = binding.txtName

        init {
            binding.root.setOnClickListener {

                onItemClick?.invoke(comics[adapterPosition])
            }
        }

    }

    /*interface onCharacterClickListener {
        fun onCharacterClick(position: Int)
    }*/

    companion object {
        val characterDiff = object: DiffUtil.ItemCallback<Comics>() {
            override fun areItemsTheSame(old: Comics, new: Comics): Boolean {
                return old.id == new.id

            }

            override fun areContentsTheSame(old: Comics, new: Comics): Boolean {
                return old == new
            }

        }
    }
}