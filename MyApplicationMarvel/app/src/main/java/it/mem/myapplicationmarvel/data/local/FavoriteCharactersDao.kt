package it.mem.myapplicationmarvel.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteCharactersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCharacter(characters: FavoriteCharacters)

    @Query("SELECT * FROM favorite_characters_table")
    fun getAllCharacters():LiveData<List<FavoriteCharacters>>

    @Query("SELECT count(*) FROM favorite_characters_table WHERE id=:characterId")
    fun checkCharacter(characterId:Int):Int

    @Delete
    fun removeFromFavorite(character:FavoriteCharacters)



}