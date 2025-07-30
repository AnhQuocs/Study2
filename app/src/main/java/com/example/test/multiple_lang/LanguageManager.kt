package com.example.test.multiple_lang

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.test.multiple_lang.for_values.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

//private val Context.dataStore by preferencesDataStore(name = "settings")
private val LANGUAGE_KEY = stringPreferencesKey("app_language")

object LanguageManager {
    suspend fun saveLanguage(context: Context, lang: AppLanguage) {
        context.dataStore.edit { it[LANGUAGE_KEY] = lang.code }
    }

    suspend fun getLanguage(context: Context): AppLanguage {
        val code = context.dataStore.data
            .map { it[LANGUAGE_KEY] ?: AppLanguage.EN.code }
            .first()
        return  AppLanguage.fromCode(code)
    }
}