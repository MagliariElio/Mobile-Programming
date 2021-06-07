package it.mem.myapplicationmarvel.data.local

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import it.mem.myapplicationmarvel.data.local.FavoriteCharacters
import it.mem.myapplicationmarvel.data.local.FavoriteCharactersDatabase
import it.mem.myapplicationmarvel.data.local.FavoriteCharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteCharacterViewModel(application: Application): AndroidViewModel(application) {
    val readAllData:LiveData<List<FavoriteCharacters>>
    private val repository: FavoriteCharactersRepository

    init {
        val favoriteCharactersDao = FavoriteCharactersDatabase.getDatabase(application).FavoriteCharactersDao()
        repository= FavoriteCharactersRepository(favoriteCharactersDao)
        readAllData = repository.getAllCharacters
    }

    fun addFavoriteCharacter(favoriteCharacters: FavoriteCharacters){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavoriteCharacter((favoriteCharacters))
        }
    }
}