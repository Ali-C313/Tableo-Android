package com.example.tableo.util

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.StringRes
import java.util.Locale

/** App-Context für Texte außerhalb von Compose, wird in di/App gesetzt. */
object AppStrings {
    lateinit var context: Context
}

/**
 * Text aus res/values in der gewählten App-Sprache, z.B. für ViewModels und Benachrichtigungen.
 *
 * @param id z.B. R.string.common_guest
 * @param formatArgs Werte für %1$s, %2$s, ...
 */
fun getString(@StringRes id: Int, vararg formatArgs: Any): String {
    val config = Configuration(AppStrings.context.resources.configuration)
    config.setLocale(Locale.getDefault())
    val localized = AppStrings.context.createConfigurationContext(config)
    return if (formatArgs.isEmpty()) localized.getString(id) else localized.getString(id, *formatArgs)
}
