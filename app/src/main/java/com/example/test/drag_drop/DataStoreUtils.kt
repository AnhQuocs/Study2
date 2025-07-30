package com.example.test.drag_drop

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore by preferencesDataStore("drag_list_prefs")
val KEY_LIST_ORDER = stringPreferencesKey("list_order")

suspend fun saveListOrder(context: Context, list: List<String>) {
    context.dataStore.edit { prefs ->
        prefs[KEY_LIST_ORDER] = list.joinToString("|")
    }
}

suspend fun getSavedListOrder(context: Context): List<String>? {
    val prefs = context.dataStore.data.first()
    return prefs[KEY_LIST_ORDER]?.split("|")
}