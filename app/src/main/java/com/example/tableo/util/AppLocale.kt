package com.example.tableo.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.tableo.data.enums.AppLanguage
import java.util.Locale

/** Gewählte Sprache der App, änderbar aus den Einstellungen. */
object LanguageState {
    var language by mutableStateOf(AppLanguage.SYSTEM)
}

// Sprache vom Handy, bevor die App sie überschreibt
private var systemLocale: Locale? = null

/**
 * Hüllt die ganze App ein: gewählte Sprache für stringResource und bei Arabisch
 * das Layout von rechts nach links. Beim Sprachwechsel wird alles neu gezeichnet.
 */
@Composable
fun AppLocaleEnvironment(content: @Composable () -> Unit) {
    val language = LanguageState.language

    if (systemLocale == null) systemLocale = Locale.getDefault()
    val locale = language.code?.let { Locale.forLanguageTag(it) } ?: systemLocale!!
    Locale.setDefault(locale)

    val configuration = LocalConfiguration.current
    configuration.setLocale(locale)
    val resources = LocalContext.current.resources
    @Suppress("DEPRECATION")
    resources.updateConfiguration(configuration, resources.displayMetrics)

    CompositionLocalProvider(
        LocalConfiguration provides configuration,
        LocalLayoutDirection provides if (language.isRightToLeft) LayoutDirection.Rtl else LocalLayoutDirection.current
    ) {
        key(language) {
            content()
        }
    }
}
