package com.jabirdev.youtubeapikotlin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jabirdev.youtubeapikotlin.activities.PlayerActivity
import com.jabirdev.youtubeapikotlin.databinding.ItemVideoBinding
import com.jabirdev.youtubeapikotlin.diffutils.VideoDiffUtil
import com.jabirdev.youtubeapikotlin.model.VideoYtModel

class VideoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var oldItems = ArrayList<VideoYtModel.VideoItem>()

    class VideoHolder(itemView: ItemVideoBinding) : RecyclerView.ViewHolder(itemView.root){
        private val binding = itemView

        fun setData(data: VideoYtModel.VideoItem){

            binding.root.setOnClickListener {
                val i = Intent(it.context, PlayerActivity::class.java)
                i.putExtra("video_id", data.videoId.id)
                i.putExtra("video_title", data.snippetYt.title)
                i.putExtra("video_description", data.snippetYt.description)
                it.context.startActivity(i)
            }

            binding.tvVideoTitle.text = data.snippetYt.title
            binding.tvPublished.text = data.snippetYt.publishedAt
            Glide.with(binding.root)
                .load(data.snippetYt.thumbnails.high.url)
                .into(binding.ivThumbnail)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VideoHolder).setData(oldItems[position])
    }

    override fun getItemCount(): Int {
        return oldItems.size
    }

    fun setData(newList: List<VideoYtModel.VideoItem>, rv: RecyclerView){
        val videoDiff = VideoDiffUtil(oldItems, newList)
        val diff = DiffUtil.calculateDiff(videoDiff)
        oldItems.addAll(newList)
        diff.dispatchUpdatesTo(this)
        rv.scrollToPosition(oldItems.size - newList.size)
    }

    fun clearAll(){
        oldItems.clear()
        notifyDataSetChanged()
    }

}