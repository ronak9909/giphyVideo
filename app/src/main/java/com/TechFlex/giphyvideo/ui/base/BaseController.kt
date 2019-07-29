package com.TechFlex.giphyvideo.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import com.TechFlex.giphyvideo.ActionBarProvider
import com.bluelinelabs.conductor.Controller

abstract class BaseController : RefWatchingController {

    // Likes: This is just a quick demo of how an ActionBar *can* be accessed, not necessarily how it *should*
    // be accessed. In a production app, this would use Dagger instead.
    protected val actionBar: ActionBar?
        get() {
            val actionBarProvider = activity as ActionBarProvider?
            return actionBarProvider?.supportActionBar
        }

    protected var title: String? = null

    protected constructor() {}

    protected constructor(args: Bundle) : super(args) {}

    override fun onAttach(view: View) {
        setTitle()
        super.onAttach(view)
    }

    protected fun setTitle() {
        var parentController = parentController
        while (parentController != null) {
            if (parentController is BaseController && parentController.title != null) {
                return
            }
            parentController = parentController.parentController
        }

        val title = title
        val actionBar = actionBar
        if (title != null && actionBar != null) {
            actionBar.title = title
        }
    }
}
