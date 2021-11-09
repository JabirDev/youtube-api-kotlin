package com.jabirdev.youtubeapikotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jabirdev.youtubeapikotlin.databinding.ItemPlaylistBinding
import com.jabirdev.youtubeapikotlin.diffutils.PlaylistDiffUtil
import com.jabirdev.youtubeapikotlin.model.PlaylistYtModel

class PlaylistAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val oldItems = ArrayList<PlaylistYtModel.PlaylistItem>()

    class PlaylistHolder(itemView: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(itemView.root){
        private val binding = itemView

        fun setData(data: PlaylistYtModel.PlaylistItem){
            binding.tvPlaylistTitle.text = data.snippetYt.title
            val videoCount = "${data.contentDetail.itemCount} videos"
            binding.tvVideoCount.text = videoCount
            Glide.with(binding.root).load(data.snippetYt.thumbnails.high.url)
                .into(binding.thumbnail)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlaylistHolder).setData(oldItems[position])
    }

    override fun getItemCount(): Int {
        return oldItems.size
    }

    fun setData(newList: List<PlaylistYtModel.PlaylistItem>, rv: RecyclerView){
        val playlistDiff = PlaylistDiffUtil(oldItems, newList)
        val diff = DiffUtil.calculateDiff(playlistDiff)
        oldItems.addAll(newList)
        diff.dispatchUpdatesTo(this)
        rv.scrollToPosition(oldItems.size - newList.size)
    }

}