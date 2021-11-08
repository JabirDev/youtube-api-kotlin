package com.jabirdev.youtubeapikotlin.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class VideoYtModel(
    @SerializedName("nextPageToken")
    val nextPageToken: String?,

    @SerializedName("items")
    val items: List<VideoItem>

) {

    data class VideoItem (
        @SerializedName("id")
        val videoId: VideoId,

        @SerializedName("snippet")
        val snippetYt: SnippetYt
    )

    data class VideoId(
        @SerializedName("videoId")
        val id: String
    )

}