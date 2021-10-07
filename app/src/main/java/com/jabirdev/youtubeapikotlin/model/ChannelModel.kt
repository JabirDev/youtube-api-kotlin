package com.jabirdev.youtubeapikotlin.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ChannelModel(

    @SerializedName("items")
    val items: List<Items>

) {
    data class Items(

        @SerializedName("id")
        val id: String,

        @SerializedName("snippet")
        val snippet: SnippetYt
    )
}