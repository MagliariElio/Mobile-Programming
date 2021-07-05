package it.mem.myapplicationmarvel.ui.comics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import it.mem.myapplicationmarvel.databinding.ItemComicsBinding
import it.mem.myapplicationmarvel.extensions.load
import it.mem.myapplicationmarvel.data.model.entity.Comics

class ComicsAdapter : PagedListAdapter<Comics, ComicsAdapter.VH>(comicDiff) {

    private lateinit var binding: ItemComicsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater=LayoutInflater.from(parent.context)
        binding= ItemComicsBinding.inflate(layoutInflater, parent, false)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comic, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val comic = getItem(position)
        holder.txtName.text=comic?.title
        holder.imgThumbnail.load("${comic?.thumbnail?.path}.${comic?.thumbnail?.extension}")

    }

    class VH(binding: ItemComicsBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgThumbnail = binding.imgThumbnail
        val txtName = binding.txtName

    }

    companion object {
        val comicDiff = object: DiffUtil.ItemCallback<Comics>() {
            override fun areItemsTheSame(old: Comics, new: Comics): Boolean {
                return old.id == new.id

            }

            override fun areContentsTheSame(old: Comics, new: Comics): Boolean {
                return old == new
            }

        }
    }
}