package com.TechFlex.giphyvideo.ui.list.domain.model

import com.google.gson.annotations.SerializedName

data class Giphy(
    @SerializedName("data") val data: List<GiphyData>) {


    data class GiphyData (
        @SerializedName("id") val id: String,
        @SerializedName("slug") val slug: String,
        @SerializedName("images") val images: Images,
        @SerializedName("title") val title: String
    )


    data class Images (
        @SerializedName("original_mp4") val orijnalMp4: OrijnalMp4,
        @SerializedName("480w_still") val still480w: Still480W
    ) {

        data class OrijnalMp4 (
            @SerializedName("width") val width: String,
            @SerializedName("height") val height: String,
            @SerializedName("mp4") val mp4: String,
            @SerializedName("mp4_size") val mp4Size: String)

        data class Still480W (
            @SerializedName("width") val width: String,
            @SerializedName("height") val height: String,
            @SerializedName("url") val url: String)
    }
}