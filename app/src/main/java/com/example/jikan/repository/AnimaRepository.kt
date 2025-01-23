package com.example.jikan.repository

import com.example.jikan.data.HomeScreenAnima.Data
import com.example.jikan.retrofit.RetrofitInstance

class AnimaRepository {
    private val api = RetrofitInstance.api

    suspend fun getTopAnima() :  List<Data>{
        return api.getTopAnime().data
    }

    suspend fun getDetailAnima(int : Int) : com.example.jikan.data.AnimeDetail.Data {
        return api.getAnimeDetails(int).data
    }
}