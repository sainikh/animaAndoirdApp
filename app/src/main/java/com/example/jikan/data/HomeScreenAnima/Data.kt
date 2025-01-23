package com.example.jikan.data.HomeScreenAnima

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("mal_id")
    val id : Int,
    val title: String,
    val episodes: Int,
    val rating: String,
    val images: Images,
)

