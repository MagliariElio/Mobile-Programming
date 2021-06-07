package it.mem.myapplicationmarvel.data.model.paging.characters

import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import it.mem.myapplicationmarvel.data.model.api.MarvelAPI
import it.mem.myapplicationmarvel.data.model.entity.Character

class CharactersDataSourceFactory(private val compositeDisposable: CompositeDisposable, private val marvelAPI: MarvelAPI, private val order: String):
    DataSource.Factory<Int, Character>() {

    override fun create(): DataSource<Int, Character> {
        return CharactersDataSource(marvelAPI, compositeDisposable, order)
    }

}