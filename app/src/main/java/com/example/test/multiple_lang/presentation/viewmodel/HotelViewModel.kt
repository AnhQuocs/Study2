package com.example.test.multiple_lang.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.multiple_lang.AppLanguage
import com.example.test.multiple_lang.Hotel
import com.example.test.multiple_lang.LanguageManager
import com.example.test.multiple_lang.domain.usecase.GetHotelsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HotelViewModel(
    private val getHotelsUseCase: GetHotelsUseCase,
    private val context: Context
): ViewModel() {

    private val _hotels = MutableStateFlow<List<Hotel>>(emptyList())
    val hotels: StateFlow<List<Hotel>> get() = _hotels

    private val _language = MutableStateFlow(AppLanguage.EN)
    val language: StateFlow<AppLanguage> get() = _language

    // KHÔNG loadHotels trong init
    init {
        viewModelScope.launch {
            _language.value = LanguageManager.getLanguage(context)
        }
    }

    // Gọi hàm này sau khi login thành công
    fun loadAfterLogin() {
        viewModelScope.launch {
            _language.value = LanguageManager.getLanguage(context)
            loadHotels()
        }
    }

    private suspend fun loadHotels() {
        val list = getHotelsUseCase()
        _hotels.value = list
    }

    fun changeLanguage(lang: AppLanguage) {
        viewModelScope.launch {
            LanguageManager.saveLanguage(context, lang)
            _language.value = lang
            loadHotels()
        }
    }
}