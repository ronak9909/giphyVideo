package com.TechFlex.giphyvideo

import android.app.Application
import butterknife.ButterKnife
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.TechFlex.giphyvideo.di.component.ApplicationComponent
import com.TechFlex.giphyvideo.di.component.DaggerApplicationComponent
import com.TechFlex.giphyvideo.di.module.ApplicationModule
import com.TechFlex.giphyvideo.ui.detail.domain.model.MyObjectBox
import io.objectbox.BoxStore

class GiphyVideoApplication : Application() {

    lateinit var component: ApplicationComponent

    companion object {
        lateinit var refWatcher: RefWatcher
        lateinit var mBoxStore: BoxStore
        lateinit var instance: GiphyVideoApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        ButterKnife.setDebug(BuildConfig.DEBUG)
        instance = this

        refWatcher = LeakCanary.install(this)
        mBoxStore = MyObjectBox.builder().androidContext(this).build();

        setup()
    }

    fun setup() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this)).build()
        component.inject(this)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return component
    }


    fun getBoxStore(): BoxStore {
        return mBoxStore
    }

    fun getApp(): GiphyVideoApplication {
        return instance
    }
}
