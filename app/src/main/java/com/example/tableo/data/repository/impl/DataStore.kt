package com.example.tableo.data.repository.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/** Lokale Einstellungen der App, z.B. Onboarding, Sprache und Lieferadresse. */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")
