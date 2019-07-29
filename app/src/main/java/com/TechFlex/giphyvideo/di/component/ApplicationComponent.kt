package com.TechFlex.giphyvideo.di.component

import com.TechFlex.giphyvideo.GiphyVideoApplication
import com.TechFlex.giphyvideo.di.module.ApplicationModule
import dagger.Component

@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: GiphyVideoApplication)

}