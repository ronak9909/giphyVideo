package com.TechFlex.giphyvideo.ui.list

import com.TechFlex.giphyvideo.api.ApiServiceInterface
import com.TechFlex.giphyvideo.ui.list.domain.model.Giphy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListPresenter: ListContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private val api: ApiServiceInterface = ApiServiceInterface.create()
    private lateinit var view: ListContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: ListContract.View) {
        this.view = view
    }

    override fun loadData(apiKey: String, query: String, limit: String, offset: String, rating: String, language: String) {
        val subscribe = api.getList(apiKey, query, limit, offset, rating, language).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list: Giphy ->
                view.showProgress(false)
                view.loadDataSuccess(list)

            }, { error ->
                view.showProgress(false)
                view.showErrorMessage(error.localizedMessage)
            })

        subscriptions.add(subscribe)
    }
}