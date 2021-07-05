package it.mem.myapplicationmarvel.ui.favorite

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.mem.myapplicationmarvel.data.local.FavoriteCharacters
import it.mem.myapplicationmarvel.data.local.FavoriteCharactersDao
import it.mem.myapplicationmarvel.data.local.FavoriteCharactersDatabase
import it.mem.myapplicationmarvel.databinding.ItemCharacterBinding
import it.mem.myapplicationmarvel.extensions.load
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteCharacterListAdapter: RecyclerView.Adapter<FavoriteCharacterListAdapter.ViewHolder>() {

    private var favoriteList= emptyList<FavoriteCharacters>()
    var onItemClick: ((FavoriteCharacters) -> Unit)? = null

    private var userDb: FavoriteCharactersDatabase? = FavoriteCharactersDatabase.INSTANCE
    private var characterDao: FavoriteCharactersDao?=userDb?.FavoriteCharactersDao()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = favoriteList[position]
        holder.tvName.text=currentItem.name
        holder.ckFavorite.isChecked=true
        holder.ivPic.load(currentItem.image)
        Log.w("prova", currentItem.image)

        holder.ckFavorite.setOnClickListener {
            removeFromFavorite(currentItem.id, currentItem.name, currentItem.image)
            holder.ckFavorite.isChecked=false
        }




    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    fun setData(favoriteCharacters: List<FavoriteCharacters>) {
        this.favoriteList=favoriteCharacters
        notifyDataSetChanged()

    }



    inner class ViewHolder(binding: ItemCharacterBinding): RecyclerView.ViewHolder(binding.root) {
        var ivPic= binding.imgThumbnail
        var tvName= binding.txtName
        var ckFavorite=binding.ckFavorite

        init {
            binding.root.setOnClickListener {

                onItemClick?.invoke(favoriteList[adapterPosition])
            }

        }

    }

    private fun removeFromFavorite(id: Int, name: String, image:String){
        CoroutineScope(Dispatchers.IO).launch {
            val favoriteCharacter= FavoriteCharacters(id, name, image)
            characterDao?.removeFromFavorite(favoriteCharacter)
        }
        Log.w("Marvel", "$name, $id rimosso")

    }

}