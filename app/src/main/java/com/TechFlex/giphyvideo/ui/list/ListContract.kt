package com.TechFlex.giphyvideo.ui.list

import com.TechFlex.giphyvideo.ui.list.domain.model.Giphy
import com.TechFlex.giphyvideo.ui.base.BaseContract

interface ListContract {

    interface View: BaseContract.View {
        fun showProgress(show: Boolean)
        fun showErrorMessage(error: String)
        fun loadDataSuccess(list: Giphy)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun loadData(apiKey: String, query: String, limit: String, offset: String, rating: String, language: String)
    }
}
