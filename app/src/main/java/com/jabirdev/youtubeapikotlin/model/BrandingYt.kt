package com.jabirdev.youtubeapikotlin.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BrandingYt(
    @SerializedName("image")
    val image: ImageBanner
) {

    data class ImageBanner (
        @SerializedName("bannerExternalUrl")
        val bannerUrl: String
        )

}