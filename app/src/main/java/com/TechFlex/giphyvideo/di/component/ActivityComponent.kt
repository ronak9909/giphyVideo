package com.TechFlex.giphyvideo.di.component

import android.app.Activity
import com.TechFlex.giphyvideo.GiphyVideoApplication
import com.TechFlex.giphyvideo.di.module.ActivityModule
import com.TechFlex.giphyvideo.di.module.ApplicationModule
import dagger.Component

@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(activity: Activity)

}