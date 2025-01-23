package com.example.jikan.data.AnimeDetail

data class Data(
    val trailer: Trailer,
    val images: Images,
    val title: String,
    val synopsis: String,
    val genres: List<Genre>,
    val episodes: Int,
    val rating: String,
)

