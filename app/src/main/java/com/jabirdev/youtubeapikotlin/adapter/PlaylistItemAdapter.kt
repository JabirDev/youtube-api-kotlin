package com.jabirdev.youtubeapikotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jabirdev.youtubeapikotlin.databinding.ItemPlaylistItemBinding
import com.jabirdev.youtubeapikotlin.diffutils.PlaylistItemDiffUtil
import com.jabirdev.youtubeapikotlin.model.PlaylistYtModel

class PlaylistItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val oldItems = ArrayList<PlaylistYtModel.PlaylistItem>()
    var currentSelected: Int? = 0
    var addListener: ItemClickListener? = null

    inner class PlaylistItemHolder(itemView: ItemPlaylistItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView
        fun setData(data: PlaylistYtModel.PlaylistItem, selected: Boolean,
                    function: (Int) -> Unit, position: Int) {

            binding.root.isSelected = selected
            binding.root.setOnClickListener {
                function(position)
                if (!selected){
                    addListener?.onClick(data)
                }
            }

            binding.tvVideoTitle.text = data.snippetYt.title
            binding.tvPublished.text = data.snippetYt.publishedAt
            Glide.with(binding.root)
                .load(data.snippetYt.thumbnails.high.url)
                .into(binding.ivThumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemPlaylistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistItemHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val function = { pos: Int ->
            if (currentSelected == null || currentSelected != pos) {
                currentSelected = pos
                notifyDataSetChanged()
            }
        }
        (holder as PlaylistItemHolder).setData(oldItems[position],position == currentSelected, function, position)
    }

    override fun getItemCount(): Int {
        return oldItems.size
    }

    fun setData(newList: List<PlaylistYtModel.PlaylistItem>, rv: RecyclerView){
        val videoDiff = PlaylistItemDiffUtil(oldItems, newList)
        val diff = DiffUtil.calculateDiff(videoDiff)
        oldItems.addAll(newList)
        diff.dispatchUpdatesTo(this)
        rv.scrollToPosition(oldItems.size - newList.size)
    }

    fun interface ItemClickListener {
        fun onClick(data: PlaylistYtModel.PlaylistItem)
    }

}