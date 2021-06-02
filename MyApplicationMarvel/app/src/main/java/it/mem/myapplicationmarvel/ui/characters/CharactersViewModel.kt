package it.mem.myapplicationmarvel.ui.characters


import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io
import it.mem.myapplicationmarvel.model.api.MarvelAPI
import it.mem.myapplicationmarvel.model.entity.Character
import it.mem.myapplicationmarvel.model.paging.CharactersDataSourceFactory

class CharactersViewModel :ViewModel(){
    var characterList:Observable<PagedList<Character>>

    private val compositeDisposable=CompositeDisposable()

    private val pageSize=20

    private val sourceFactory:CharactersDataSourceFactory = CharactersDataSourceFactory(compositeDisposable, MarvelAPI.getService())

    init {

        val config=PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize*2)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        characterList=RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(io())
            .buildObservable()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}