package com.TechFlex.giphyvideo.di.module

import android.app.Application
import com.TechFlex.giphyvideo.GiphyVideoApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val baseApp: GiphyVideoApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return baseApp
    }
}