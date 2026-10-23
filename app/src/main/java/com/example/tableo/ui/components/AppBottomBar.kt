package com.example.tableo.ui.components

import com.example.tableo.R
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tableo.data.enums.BottomTab
import com.example.tableo.ui.theme.AppElevation
import com.example.tableo.ui.theme.AppShape
import com.example.tableo.ui.theme.AppSpacing

/**
 * Schwebende, abgerundete Navigationsleiste statt der flachen Material3-Standardleiste.
 *
 * @param selectedTab Aktuell aktiver Reiter (bekommt die farbige Hervorhebung)
 */
@Composable
fun AppBottomBar(
    selectedTab: BottomTab,
    onHomeClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = AppSpacing.L, vertical = AppSpacing.M),
        shape = AppShape.Large,
        color = MaterialTheme.colorScheme.surfaceContainer,
        shadowElevation = AppElevation.Floating
    ) {
        Row(
            modifier = Modifier.padding(vertical = AppSpacing.S + AppSpacing.XS),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomBarItem(
                Icons.Filled.Home,
                stringResource(R.string.bottom_bar_home),
                selectedTab == BottomTab.HOME,
                onHomeClick
            )
            BottomBarItem(
                Icons.AutoMirrored.Filled.List,
                stringResource(R.string.bottom_bar_orders),
                selectedTab == BottomTab.ORDERS,
                onOrdersClick
            )
            BottomBarItem(
                Icons.Filled.AccountCircle,
                stringResource(R.string.bottom_bar_account),
                selectedTab == BottomTab.SETTINGS,
                onSettingsClick
            )
        }
    }
}

@Preview
@Composable
private fun AppBottomBarPreview() {
    AppBottomBar(
        selectedTab = BottomTab.HOME,
        onHomeClick = { },
        onOrdersClick = { },
        onSettingsClick = { }
    )
}
