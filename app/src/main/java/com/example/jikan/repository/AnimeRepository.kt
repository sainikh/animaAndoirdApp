package com.example.jikan.repository

import com.example.jikan.data.HomeScreenAnime.Data
import com.example.jikan.retrofit.RetrofitInstance

class AnimeRepository {
    private val api = RetrofitInstance.api

    suspend fun getTopAnime(): List<Data> {
        return api.getTopAnime().data
    }

    suspend fun getDetailAnime(int: Int): com.example.jikan.data.AnimeDetail.Data {
        return api.getAnimeDetails(int).data
    }
}