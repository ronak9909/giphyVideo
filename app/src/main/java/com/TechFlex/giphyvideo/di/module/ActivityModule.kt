package com.TechFlex.giphyvideo.di.module

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }
}
