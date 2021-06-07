package it.mem.myapplicationmarvel.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="favorite_characters_table")
data class FavoriteCharacters (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val image:String
)