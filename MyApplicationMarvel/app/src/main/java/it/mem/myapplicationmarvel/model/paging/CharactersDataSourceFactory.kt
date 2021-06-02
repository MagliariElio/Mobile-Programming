package it.mem.myapplicationmarvel.model.paging

import android.view.accessibility.AccessibilityRequestPreparer
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import it.mem.myapplicationmarvel.model.api.MarvelAPI
import it.mem.myapplicationmarvel.model.entity.Character

class CharactersDataSourceFactory (private val compositeDisposable: CompositeDisposable, private val marvelAPI: MarvelAPI):
    DataSource.Factory<Int, Character>() {

    override fun create(): DataSource<Int, Character> {
        return CharactersDataSource(marvelAPI, compositeDisposable)
    }

}