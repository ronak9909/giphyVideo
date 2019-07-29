package com.TechFlex.giphyvideo.di.component

import android.app.Activity
import androidx.fragment.app.Fragment
import com.TechFlex.giphyvideo.GiphyVideoApplication
import com.TechFlex.giphyvideo.di.module.ActivityModule
import com.TechFlex.giphyvideo.di.module.ApplicationModule
import com.TechFlex.giphyvideo.di.module.FragmentModule
import com.TechFlex.giphyvideo.ui.list.ListFragment
import dagger.Component

@Component(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {
    fun inject(fragment: ListFragment)
}