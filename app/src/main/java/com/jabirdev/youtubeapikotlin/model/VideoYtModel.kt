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
        @SerializedName("snippet")
        val snippetYt: SnippetYt
    )

}