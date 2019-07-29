package com.TechFlex.giphyvideo.ui.base

import android.os.Bundle
import com.TechFlex.giphyvideo.GiphyVideoApplication
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType

abstract class RefWatchingController : ButterKnifeController {

    private var hasExited: Boolean = false

    protected constructor() {}
    protected constructor(args: Bundle) : super(args) {}

    public override fun onDestroy() {
        super.onDestroy()

        if (hasExited) {
            GiphyVideoApplication.refWatcher.watch(this)
        }
    }

    override fun onChangeEnded(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeEnded(changeHandler, changeType)

        hasExited = !changeType.isEnter
        if (isDestroyed) {
            GiphyVideoApplication.refWatcher.watch(this)
        }
    }
}
