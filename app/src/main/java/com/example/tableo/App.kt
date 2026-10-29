package com.example.tableo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.tableo.navigation.AppNavigation
import com.example.tableo.ui.AppViewModel
import com.example.tableo.ui.theme.TableoTheme
import com.example.tableo.util.AppLocaleEnvironment
import org.koin.compose.viewmodel.koinViewModel

/** Oberste Composable der App, wird von MainActivity gestartet. */
@Composable
fun App(
    appViewModel: AppViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        appViewModel.loadSettings()
    }

    AppLocaleEnvironment {
        TableoTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                AppNavigation()
            }
        }
    }
}
