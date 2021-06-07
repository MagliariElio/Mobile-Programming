package it.mem.myapplicationmarvel.ui.characters


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io
import it.mem.myapplicationmarvel.data.model.api.MarvelAPI
import it.mem.myapplicationmarvel.data.model.entity.Character
import it.mem.myapplicationmarvel.data.model.paging.characters.CharactersDataSourceFactory
import it.mem.myapplicationmarvel.data.model.paging.characters.charactersbyname.CharactersDataByNameSourceFactory

class CharactersViewModel(application: Application) :AndroidViewModel(application){
    //var characterList:Observable<PagedList<Character>>

    private val compositeDisposable = CompositeDisposable()




    fun getList(name:String):Observable<PagedList<Character>> {


        val pageSize = 20

        val sourceFactory: CharactersDataSourceFactory = CharactersDataSourceFactory(compositeDisposable, MarvelAPI.getService())

        val sourceByNameFactory: CharactersDataByNameSourceFactory = CharactersDataByNameSourceFactory(compositeDisposable, MarvelAPI.getService(), name)

        Log.d("Marvel", "$name")

        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(false)
                .build()
        if (name=="") {
            return RxPagedListBuilder(sourceFactory, config)
                    .setFetchScheduler(io())
                    .buildObservable()
        }
        return RxPagedListBuilder(sourceByNameFactory, config)
                .setFetchScheduler(io())
                .buildObservable()


    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }




}