package com.jabirdev.youtubeapikotlin.ui.playlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jabirdev.youtubeapikotlin.R
import com.jabirdev.youtubeapikotlin.adapter.PlaylistAdapter
import com.jabirdev.youtubeapikotlin.databinding.FragmentPlaylistBinding
import java.lang.StringBuilder

class PlaylistFragment : Fragment() {

    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!
    private var playlistViewModel: PlaylistViewModel? = null
    private val adapter = PlaylistAdapter()
    private var isLoading = false
    private var isScroll = false
    private var currentItem = -1
    private var totalItem = -1
    private var scrollOutItem = -1
    private var isAllVideoLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = LinearLayoutManager(requireContext())
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
                            playlistViewModel?.getPlaylist()
                        } else {
                            Toast.makeText(requireContext(), "All playlist loaded", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })

        playlistViewModel?.playlist?.observe(viewLifecycleOwner, {
            adapter.setData(it?.items!!, binding.rvPlaylist)
            it.nextPageToken?.let { token ->
                Log.e("next page token", token)
            }
        })

        playlistViewModel?.isLoading?.observe(viewLifecycleOwner, {
            isLoading = it
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        playlistViewModel?.isAllPlaylistLoaded?.observe(viewLifecycleOwner, {
            isAllVideoLoaded = it
            if (it) Toast.makeText(requireContext(), "All playlist has been loaded", Toast.LENGTH_SHORT).show()
        })

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        playlistViewModel =
                ViewModelProvider(this).get(PlaylistViewModel::class.java)
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }
}