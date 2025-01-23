package com.example.jikan.retrofit
import com.example.jikan.data.AnimeDetail.DetailAnimaResponse
import com.example.jikan.data.HomeScreenAnima.HomeScreenAnimaResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/v4/top/anime")
    suspend fun getTopAnime(): HomeScreenAnimaResponse

    @GET("/v4/anime/{id}")
    suspend fun getAnimeDetails(@Path("id") id: Int): DetailAnimaResponse
}