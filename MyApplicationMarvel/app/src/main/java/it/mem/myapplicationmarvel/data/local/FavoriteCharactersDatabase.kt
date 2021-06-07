package it.mem.myapplicationmarvel.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCharacters::class], version = 1)
abstract class FavoriteCharactersDatabase() : RoomDatabase(){

    abstract fun FavoriteCharactersDao(): FavoriteCharactersDao

    companion object{
        var INSTANCE: FavoriteCharactersDatabase?=null

        fun getDatabase(context: Context): FavoriteCharactersDatabase {
            val tempInstance= INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteCharactersDatabase::class.java,
                    "favorite_characters_database"
                ).build()
                INSTANCE =instance
                return instance
            }
        }
    }

}