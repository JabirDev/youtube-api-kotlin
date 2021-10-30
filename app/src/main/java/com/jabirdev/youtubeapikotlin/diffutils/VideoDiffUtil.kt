package com.jabirdev.youtubeapikotlin.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.jabirdev.youtubeapikotlin.model.VideoYtModel

class VideoDiffUtil(
    private val oldList: List<VideoYtModel.VideoItem>,
    private val newList: List<VideoYtModel.VideoItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldVideo = oldList[oldItemPosition]
        val newVideo = newList[newItemPosition]
        return oldVideo.snippetYt.title == newVideo.snippetYt.title
    }
}