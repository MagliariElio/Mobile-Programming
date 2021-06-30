package it.mem.myapplicationmarvel.data.model.paging.characters.infobyid

import android.util.Log
import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import it.mem.myapplicationmarvel.data.model.api.MarvelAPI
import it.mem.myapplicationmarvel.data.model.entity.Comics

class StoriesByCharactersDataSource (private val marvelAPI: MarvelAPI, private val compositeDisposable: CompositeDisposable, private val id:Int):
    PageKeyedDataSource<Int, Comics>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Comics>
    ) {
        val numberOfItems=params.requestedLoadSize
        createObservable(0,1,numberOfItems, callback, null)

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Comics>) {
        val page=params.key
        val numberOfItems=params.requestedLoadSize
        createObservable(page,page-1,numberOfItems, null, callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Comics>) {

        val page=params.key
        val numberOfItems=params.requestedLoadSize
        createObservable(page,page+1,numberOfItems, null, callback)
    }

    private fun createObservable(requestedPage:Int,
                                 adjacentPage:Int,
                                 requestedLoadSize:Int,
                                 initialCallback: LoadInitialCallback<Int, Comics>?,
                                 callback: LoadCallback<Int, Comics>?){
        compositeDisposable.add(
            marvelAPI.characterComics(id,requestedPage*requestedLoadSize)
                .subscribe(
                    {response->
                        Log.d("NGVL", "Loading page: $requestedPage")
                        initialCallback?.onResult(response.data.results, null, adjacentPage)
                        callback?.onResult(response.data.results, adjacentPage)
                    }, {t->
                        Log.d("Marvel", "Error loading page: $requestedPage", t)
                    })
        )
    }

}