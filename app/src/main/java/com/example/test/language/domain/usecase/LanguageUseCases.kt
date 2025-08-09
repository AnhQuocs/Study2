package com.example.test.language.domain.usecase

import javax.inject.Inject

data class LanguageUseCases @Inject constructor(
    val getLanguage: GetLanguageUseCase,
    val updateLanguage: UpdateLanguageUseCase
)