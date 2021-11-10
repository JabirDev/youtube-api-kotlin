package com.jabirdev.youtubeapikotlin.activities

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.jabirdev.youtubeapikotlin.activities.viewmodel.PlaylistItemViewModel
import com.jabirdev.youtubeapikotlin.adapter.PlaylistItemAdapter
import com.jabirdev.youtubeapikotlin.databinding.ActivityPlaylistItemBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener

class PlaylistItemActivity : AppCompatActivity(), YouTubePlayerListener {

    private var _binding: ActivityPlaylistItemBinding? = null
    private val binding get() = _binding!!
    private var playlistItemViewModel: PlaylistItemViewModel? = null
    private var youtubePlayer: YouTubePlayer? = null
    private val adapter = PlaylistItemAdapter()
    private var isLoading = false
    private var isScroll = false
    private var currentItem = -1
    private var totalItem = -1
    private var scrollOutItem = -1
    private var isAllVideoLoaded = false
    private var isPlayingVideo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlaylistItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        lifecycle.addObserver(binding.youtubePlayer)

        binding.youtubePlayer.addYouTubePlayerListener(this)

        playlistItemViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(PlaylistItemViewModel::class.java)

        val manager = LinearLayoutManager(this, HORIZONTAL, false)
        binding.rvPlaylist.adapter = adapter
        binding.rvPlaylist.layoutManager = manager

        binding.rvPlaylist.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScroll = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItem = manager.childCount
                totalItem = manager.itemCount
                scrollOutItem = manager.findFirstVisibleItemPosition()
                if (isScroll && (currentItem + scrollOutItem == totalItem)){
                    isScroll = false
                    if (!isLoading){
                        if (!isAllVideoLoaded){
                            val playlistId = intent.getStringExtra("playlist_id")
                            playlistId?.let { playlistItemViewModel?.getPlaylistItem(it) }
                        } else {
                            Toast.makeText(this@PlaylistItemActivity, "All video loaded", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })

        adapter.addListener = PlaylistItemAdapter.ItemClickListener { data ->
            data.contentDetail.videoId?.let { id ->
                youtubePlayer?.loadVideo(id, 0f)
            }
            binding.tvVideoTitle.text = data.snippetYt.title
            binding.tvVideoDescription.text = data.snippetYt.description
            binding.tvVideoDescription.movementMethod = ScrollingMovementMethod()
        }

        playlistItemViewModel?.playlistItem?.observe(this, {
            it?.items?.let { it1 -> adapter.setData(it1, binding.rvPlaylist) }
            if (!isPlayingVideo){
                isPlayingVideo = true
                it?.items?.get(0)?.contentDetail?.videoId?.let { it1 ->
                    youtubePlayer?.loadVideo(it1, 0f)
                }
                binding.tvVideoTitle.text = it?.items?.get(0)?.snippetYt?.title
                binding.tvVideoDescription.text = it?.items?.get(0)?.snippetYt?.description
                binding.tvVideoDescription.movementMethod = ScrollingMovementMethod()
            }
        })

        playlistItemViewModel?.isLoading?.observe(this, {
            isLoading = it
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        playlistItemViewModel?.isAllItemLoaded?.observe(this, {
            isAllVideoLoaded = it
            if (it) Toast.makeText(this, "All video has been loaded", Toast.LENGTH_SHORT).show()
        })


    }

    override fun onApiChange(youTubePlayer: YouTubePlayer) {

    }

    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {

    }

    override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {

    }

    override fun onPlaybackQualityChange(
        youTubePlayer: YouTubePlayer,
        playbackQuality: PlayerConstants.PlaybackQuality
    ) {

    }

    override fun onPlaybackRateChange(
        youTubePlayer: YouTubePlayer,
        playbackRate: PlayerConstants.PlaybackRate
    ) {
    }

    override fun onReady(youTubePlayer: YouTubePlayer) {
        youtubePlayer = youTubePlayer
        val playlistId = intent.getStringExtra("playlist_id")
        playlistId?.let {
            playlistItemViewModel?.getPlaylistItem(it)
        }
    }

    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
        if (state == PlayerConstants.PlayerState.PLAYING) isPlayingVideo = true
    }

    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {

    }

    override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
        isPlayingVideo = videoId.isNotEmpty()
    }

    override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {

    }
}