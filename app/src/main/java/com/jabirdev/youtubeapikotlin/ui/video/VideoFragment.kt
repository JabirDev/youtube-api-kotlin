package com.jabirdev.youtubeapikotlin.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jabirdev.youtubeapikotlin.R
import com.jabirdev.youtubeapikotlin.adapter.VideoAdapter
import com.jabirdev.youtubeapikotlin.databinding.FragmentVideoBinding
import java.lang.StringBuilder

class VideoFragment : Fragment() {

    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!
    private var videoViewModel: VideoViewModel? = null
    private val adapter = VideoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvVideo.adapter = adapter
        binding.rvVideo.layoutManager = LinearLayoutManager(requireContext())

        videoViewModel?.video?.observe(viewLifecycleOwner, {
            if (it != null && it.items.isNotEmpty()){
                adapter.setData(it.items)
            }
        })

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        videoViewModel =
                ViewModelProvider(this).get(VideoViewModel::class.java)
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }
}