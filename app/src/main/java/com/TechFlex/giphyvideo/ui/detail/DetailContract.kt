package com.TechFlex.giphyvideo.ui.detail

import com.TechFlex.giphyvideo.ui.base.BaseContract

interface DetailContract {
    interface View: BaseContract.View {
        fun showProgress(show: Boolean)
        fun showErrorMessage(error: String)
        fun releasePlayer()
        fun initializePlayer()
        fun updateStartPosition()
        fun addUpvote()
        fun addDownVote()
        fun getAllVotes()
    }
}
