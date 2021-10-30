package com.jabirdev.youtubeapikotlin.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jabirdev.youtubeapikotlin.R

class VideoFragment : Fragment() {

    private lateinit var videoViewModel: VideoViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        videoViewModel =
                ViewModelProvider(this).get(VideoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_video, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        videoViewModel.channel.observe(viewLifecycleOwner, Observer {
            if (it != null && it.items.isNotEmpty()){
                it.items.forEach {  channel ->
                    textView.text = channel.snippet.title
                }
            }
        })
        return root
    }
}