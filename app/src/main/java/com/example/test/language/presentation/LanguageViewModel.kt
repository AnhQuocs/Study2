package com.example.test.language.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.language.domain.model.AppLanguage
import com.example.test.language.domain.usecase.GetLanguageUseCase
import com.example.test.language.domain.usecase.UpdateLanguageUseCase
import com.example.test.language.utils.LangUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getLanguageUseCase: GetLanguageUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase
): ViewModel() {

    private val _currentLanguage = MutableStateFlow(AppLanguage.ENGLISH)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    init {
        viewModelScope.launch {
            getLanguageUseCase().collect {
                LangUtils.currentLang = it.code
                _currentLanguage.value = it
            }
        }
    }

    fun changeLanguage(language: AppLanguage) {
        viewModelScope.launch {
            updateLanguageUseCase(language)
        }
    }
}