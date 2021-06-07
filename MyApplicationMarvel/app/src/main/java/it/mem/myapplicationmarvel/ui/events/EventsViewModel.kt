package it.mem.myapplicationmarvel.ui.events


import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io
import it.mem.myapplicationmarvel.data.model.api.MarvelAPI
import it.mem.myapplicationmarvel.data.model.entity.Events
import it.mem.myapplicationmarvel.data.model.paging.events.EventsDataSourceFactory

class EventsViewModel :ViewModel(){
    var eventList:Observable<PagedList<Events>>

    private val compositeDisposable=CompositeDisposable()

    private val pageSize=20

    private val sourceFactory: EventsDataSourceFactory =EventsDataSourceFactory(compositeDisposable, MarvelAPI.getService())

    init {

        val config=PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize*2)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        eventList=RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(io())
            .buildObservable()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}