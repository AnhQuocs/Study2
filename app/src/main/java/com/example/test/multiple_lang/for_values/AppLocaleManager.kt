package com.example.test.multiple_lang.for_values

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.core.os.LocaleListCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.test.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppLocaleManager {
    fun setLocale(context: Context, languageCode: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)
                .applicationLocales = LocaleList.forLanguageTags(languageCode)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(languageCode)
            )
        }
    }
}

val Context.dataStore by preferencesDataStore("settings")

object Keys {
    val LANG = stringPreferencesKey("lang")
}

suspend fun Context.saveLang(code: String) {
    dataStore.edit { it[Keys.LANG] = code }
}

fun Context.currentLangFlow() = dataStore.data.map { it[Keys.LANG] ?: "en" }

@Composable
fun MyApp(rootContext: Context = LocalContext.current) {
    val localeManager = remember { AppLocaleManager() }
    val lang by rootContext.currentLangFlow().collectAsState(initial = "en")

    LaunchedEffect(lang) {
        localeManager.setLocale(rootContext, lang)
    }

    CompositionLocalProvider(
        LocalLayoutDirection provides if (lang == "ar") LayoutDirection.Rtl else LayoutDirection.Ltr
    ) {
        // Thay thế AppNavigation bằng màn LanguageScreen
        LanguageScreen(current = lang, onChange = { newLang ->
            CoroutineScope(Dispatchers.IO).launch {
                rootContext.saveLang(newLang)
            }
        })
    }
}

@Composable
fun LanguageScreen(current: String, onChange: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.welcome),
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = stringResource(id = R.string.button_ok),
            modifier = Modifier.padding(16.dp)
        )

        listOf(
            "en" to stringResource(id = R.string.english),
            "vi" to stringResource(id = R.string.vietnamese)
        ).forEach { (code, label) ->
            Button(
                onClick = { onChange(code) },
                colors = if (current == code) ButtonDefaults.buttonColors(Color.Blue)
                else ButtonDefaults.buttonColors(Color.Gray),
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Text(text = label)
            }
        }
    }
}