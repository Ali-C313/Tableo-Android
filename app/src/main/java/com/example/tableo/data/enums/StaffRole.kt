package com.example.tableo.data.enums

import com.example.tableo.R
import androidx.compose.runtime.Composable
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource

/** Aufgabe eines Mitarbeiters im Restaurant, wird im Team-Reiter vergeben. */
enum class StaffRole(@StringRes val labelRes: Int) {
    OHNE(R.string.role_none),
    LEITUNG(R.string.role_lead),
    KELLNER(R.string.role_waiter),
    KOCH(R.string.role_cook),
    KUECHENHILFE(R.string.role_kitchen_help),
    BAR(R.string.role_bar),
    LIEFERANT(R.string.role_driver)
}

/** Name der Rolle in der eingestellten Sprache. */
val StaffRole.label: String
    @Composable get() = stringResource(labelRes)
