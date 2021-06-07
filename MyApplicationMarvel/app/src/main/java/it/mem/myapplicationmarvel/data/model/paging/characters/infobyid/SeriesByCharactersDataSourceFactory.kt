package it.mem.myapplicationmarvel.data.model.paging.characters.infobyid

import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import it.mem.myapplicationmarvel.data.model.api.MarvelAPI
import it.mem.myapplicationmarvel.data.model.entity.Comics

class SeriesByCharactersDataSourceFactory (private val compositeDisposable: CompositeDisposable, private val marvelAPI: MarvelAPI, private val id: Int):
    DataSource.Factory<Int, Comics>() {

    override fun create(): DataSource<Int, Comics> {
        return SeriesByCharactersDataSource(marvelAPI, compositeDisposable, id)
    }

}