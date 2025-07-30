package com.example.test.multiple_lang.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.multiple_lang.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SignUpUiState {
    object Idle : SignUpUiState()
    object Loading : SignUpUiState()
    object Success : SignUpUiState()
    data class Error(val message: String) : SignUpUiState()
}

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var uiState by mutableStateOf<SignUpUiState>(SignUpUiState.Idle)
        private set

    fun onSignUpClick() {
        viewModelScope.launch {
            uiState = SignUpUiState.Loading

            val result = registerUserUseCase(username, email, password)
            uiState = if (result.isSuccess) {
                SignUpUiState.Success
            } else {
                SignUpUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}