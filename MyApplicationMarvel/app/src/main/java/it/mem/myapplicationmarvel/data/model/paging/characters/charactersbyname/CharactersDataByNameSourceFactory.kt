package it.mem.myapplicationmarvel.data.model.paging.characters.charactersbyname

import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import it.mem.myapplicationmarvel.data.model.api.MarvelAPI
import it.mem.myapplicationmarvel.data.model.entity.Character

class CharactersDataByNameSourceFactory(private val compositeDisposable: CompositeDisposable, private val marvelAPI: MarvelAPI, private val name: String, private val order: String):
    DataSource.Factory<Int, Character>() {

    override fun create(): DataSource<Int, Character> {
        return CharactersDataByNameSource(marvelAPI, compositeDisposable, name, order)
    }

}