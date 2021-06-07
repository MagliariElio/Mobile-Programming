package it.mem.myapplicationmarvel.data.local

import androidx.lifecycle.LiveData
import it.mem.myapplicationmarvel.data.local.FavoriteCharacters
import it.mem.myapplicationmarvel.data.local.FavoriteCharactersDao

class FavoriteCharactersRepository (private val favoriteCharactersDao: FavoriteCharactersDao){

    val getAllCharacters:LiveData<List<FavoriteCharacters>> = favoriteCharactersDao.getAllCharacters()

    suspend fun addFavoriteCharacter(favoriteCharacters: FavoriteCharacters){
        favoriteCharactersDao.addCharacter(favoriteCharacters)
    }

}