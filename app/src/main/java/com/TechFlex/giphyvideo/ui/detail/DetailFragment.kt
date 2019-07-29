package com.TechFlex.giphyvideo.ui.detail

import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.TechFlex.giphyvideo.GiphyVideoApplication
import com.TechFlex.giphyvideo.R
import com.TechFlex.giphyvideo.ui.detail.domain.model.Likes
import com.TechFlex.giphyvideo.ui.base.BaseController
import com.TechFlex.giphyvideo.ui.detail.domain.model.Likes_
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class DetailFragment(val imageId: String, val videoURL: String) : BaseController(), DetailContract.View {

    @BindView(R.id.playerView)
    lateinit var mPlayerView: PlayerView

    @BindView(R.id.txt_thumb_up_counter)
    lateinit var mThumbUpCounter: TextView

    @BindView(R.id.txt_thumb_down_counter)
    lateinit var mThumbDownCounter: TextView

    @BindView(R.id.img_thumb_up)
    lateinit var mThumbUpImg: ImageView

    @BindView(R.id.img_thumb_down)
    lateinit var mThumbDownImg: ImageView

    private lateinit var player: SimpleExoPlayer
    private lateinit var mediaDataSourceFactory: DataSource.Factory

    private var trackSelector: DefaultTrackSelector? = null
    private var lastSeenTrackGroupArray: TrackGroupArray? = null
    private val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory()
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0

    private lateinit var mBoxStore : BoxStore
    private lateinit var mLikeBox: Box<Likes>
    private lateinit var mLikesQuery: Query<Likes>

    protected constructor() : this("", "") {}

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override protected fun onViewBound(view: View) {
        super.onViewBound(view)

        initializePlayer()

        mBoxStore = GiphyVideoApplication.instance.getBoxStore()
        mLikeBox = mBoxStore.boxFor(Likes::class.java)

        getAllVotes()
    }


    /**
     * Initialize exo player and set all controls..
     */
    override fun initializePlayer() {
        trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        player = ExoPlayerFactory.newSimpleInstance(activity, trackSelector)
        mPlayerView.player = player
        val defaultExtractorFactory = DefaultExtractorsFactory()
        mediaDataSourceFactory = DefaultDataSourceFactory(activity, Util.getUserAgent(activity, "mediaPlayerSample"))
        val mediaSource =  ExtractorMediaSource(Uri.parse(videoURL), mediaDataSourceFactory, defaultExtractorFactory, null, null)

        mPlayerView.setShutterBackgroundColor(Color.TRANSPARENT)
        mPlayerView.requestFocus()
        //ivHideControllerButton.setOnClickListener { playerView.hideController() }
        lastSeenTrackGroupArray = null

        with(player) {
            prepare(mediaSource, false, false)
            playWhenReady = true
        }
    }

    override fun showProgress(show: Boolean) {

    }

    override fun showErrorMessage(error: String) {

    }

    override fun releasePlayer() {
        updateStartPosition()
        player.release()
        trackSelector = null
    }

    override fun updateStartPosition() {
        with(player) {
            playbackPosition = currentPosition
            currentWindow = currentWindowIndex
            playWhenReady = playWhenReady
        }
    }


    @OnClick(R.id.img_thumb_up)
    fun onThumbUpClick() {
        addUpvote()
    }


    @OnClick(R.id.img_thumb_down)
    fun onThumbDownClick() {
        addDownVote()
    }


    override fun addUpvote() {
        val likes = Likes(videoId = imageId, totalLikes = 1, totalDislikes = 0)
        mLikeBox.put(likes)

        getAllVotes()
        Log.d("INSERTED ID- ", "Inserted new Like, ID: " + likes.id)
    }

    override fun addDownVote() {
        val likes = Likes(videoId = imageId, totalLikes = 0, totalDislikes = 1)
        mLikeBox.put(likes)

        getAllVotes()
        Log.d("INSERTED ID- ", "Inserted new Dislike, ID: " + likes.id)
    }

    override fun getAllVotes() {
        val totalLikes = mLikeBox.query()
            .equal(Likes_.videoId, imageId)
            .equal(Likes_.totalLikes, 1)
            .build().property(Likes_.totalLikes).count()

        mThumbUpCounter.text = totalLikes.toString()
        if (totalLikes > 0) {
            mThumbUpImg.setImageResource(R.drawable.ic_thumb_up_filled_24dp)
        }


        val totalDislike = mLikeBox.query()
            .equal(Likes_.videoId, imageId)
            .equal(Likes_.totalDislikes, 1)
            .build().property(Likes_.totalDislikes).count()

        mThumbDownCounter.text = totalDislike.toString()
        if (totalDislike > 0) {
            mThumbDownImg.setImageResource(R.drawable.ic_thumb_down_filled_24dp)
        }

    }


    override fun onDestroy() {
        super.onDestroy()

        releasePlayer()
    }
}