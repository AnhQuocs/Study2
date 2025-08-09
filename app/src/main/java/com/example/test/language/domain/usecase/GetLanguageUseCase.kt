package com.example.test.language.domain.usecase

import com.example.test.language.data.prefecences.LanguagePreferenceManager
import com.example.test.language.domain.model.AppLanguage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLanguageUseCase @Inject constructor(
    private val manager: LanguagePreferenceManager
) {
    operator fun invoke(): Flow<AppLanguage> = manager.languageFlow
}

class UpdateLanguageUseCase @Inject constructor(
    private val manager: LanguagePreferenceManager
) {
    suspend operator fun invoke(language: AppLanguage) = manager.saveLanguage(language)
}