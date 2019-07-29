package com.TechFlex.giphyvideo.di.module

import com.TechFlex.giphyvideo.api.ApiServiceInterface
import com.TechFlex.giphyvideo.ui.list.ListContract
import com.TechFlex.giphyvideo.ui.list.ListPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    fun provideListPresenter(): ListContract.Presenter {
        return ListPresenter()
    }

    @Provides
    fun provideApiService(): ApiServiceInterface {
        return ApiServiceInterface.create()
    }
}