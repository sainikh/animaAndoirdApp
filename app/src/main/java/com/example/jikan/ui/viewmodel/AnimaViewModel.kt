package com.example.jikan.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikan.data.HomeScreenAnima.Data
import com.example.jikan.repository.AnimaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class AnimaViewModel : ViewModel() {
    private val repository = AnimaRepository()
    private var _animaList = MutableStateFlow<List<Data>>(emptyList())
    val animaList: StateFlow<List<Data>> = _animaList
    private var _animaDetail = MutableStateFlow<com.example.jikan.data.AnimeDetail.Data?>(null)
    val animaDetail: StateFlow<com.example.jikan.data.AnimeDetail.Data?> = _animaDetail
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    fun fetchTopAnima() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _animaList.value = repository.getTopAnima()
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

    fun fetchAnimaDetails(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _animaDetail.update { repository.getDetailAnima(id) }
                _isLoading.value = false
            } catch (e: UnknownHostException) {
                _errorMessage.value = "No internet"
                _animaDetail.value = null
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _animaDetail.value = null
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}