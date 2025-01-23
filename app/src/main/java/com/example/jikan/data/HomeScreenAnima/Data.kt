package com.example.jikan.data.HomeScreenAnime

import com.example.jikan.data.HomeScreenAnima.Images
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("mal_id")
    val id : Int,
    val title: String,
    val episodes: Int,
    val rating: String,
    val images: Images,
)

