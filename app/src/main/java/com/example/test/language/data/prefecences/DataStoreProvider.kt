package com.example.test.language.data.prefecences

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.languageDataStore by preferencesDataStore(name = "settings")