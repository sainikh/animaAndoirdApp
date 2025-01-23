package com.example.jikan.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikan.data.HomeScreenAnime.Data
import com.example.jikan.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class AnimeViewModel : ViewModel() {
    private val repository = AnimeRepository()
    private var _animeList = MutableStateFlow<List<Data>>(emptyList())
    val animeList: StateFlow<List<Data>> = _animeList
    private var _animeDetail = MutableStateFlow<com.example.jikan.data.AnimeDetail.Data?>(null)
    val animeDetail: StateFlow<com.example.jikan.data.AnimeDetail.Data?> = _animeDetail
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    fun fetchTopAnime() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _animeList.value = repository.getTopAnime()
                _isLoading.value = false
            } catch (e: UnknownHostException) {
                _errorMessage.value = "No internet"
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
            }
        }
    }

    fun fetchAnimeDetails(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _animeDetail.update { repository.getDetailAnime(id) }
                _isLoading.value = false
            } catch (e: UnknownHostException) {
                _errorMessage.value = "No internet"
                _animeDetail.value = null
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _animeDetail.value = null
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}