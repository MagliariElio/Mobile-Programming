package it.mem.myapplicationmarvel.data.model.paging.comics

import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import it.mem.myapplicationmarvel.data.model.api.MarvelAPI
import it.mem.myapplicationmarvel.data.model.entity.Comics

class ComicsDataSourceFactory (private val compositeDisposable: CompositeDisposable, private val marvelAPI: MarvelAPI):
    DataSource.Factory<Int, Comics>() {

    override fun create(): DataSource<Int, Comics> {
        return ComicsDataSource(marvelAPI, compositeDisposable)
    }

}