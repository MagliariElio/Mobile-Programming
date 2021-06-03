package it.mem.myapplicationmarvel.model.paging.characters.charactersbyname

import android.util.Log
import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import it.mem.myapplicationmarvel.model.api.MarvelAPI
import it.mem.myapplicationmarvel.model.entity.Character

class CharactersDataByNameSource (private val marvelAPI: MarvelAPI, private val compositeDisposable: CompositeDisposable, private val name:String):
    PageKeyedDataSource<Int, Character>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        val numberOfItems=params.requestedLoadSize
        createObservable(0,1,numberOfItems, callback, null)

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page=params.key
        val numberOfItems=params.requestedLoadSize
        createObservable(page,page-1,numberOfItems, null, callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {

        val page=params.key
        val numberOfItems=params.requestedLoadSize
        createObservable(page,page+1,numberOfItems, null, callback)
    }

    private fun createObservable(requestedPage:Int,
                              adjacentPage:Int,
                              requestedLoadSize:Int,
                              initialCallback:LoadInitialCallback<Int,Character>?,
                              callback: LoadCallback<Int, Character>?){
        compositeDisposable.add(
            marvelAPI.searchCharacters(name,requestedPage*requestedLoadSize)
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