package com.example.tableo.data.enums

import com.example.tableo.R
import androidx.compose.runtime.Composable
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource

/** Auswählbare Zeiträume fürs Statistik-Diagramm. */
enum class AnalyticsPeriod(@StringRes val labelRes: Int, val millis: Long, val bucketCount: Int) {
    LAST_24H(R.string.period_24h, 24L * 60 * 60 * 1000, 6),
    LAST_3D(R.string.period_3d, 3L * 24 * 60 * 60 * 1000, 3),
    LAST_MONTH(R.string.period_month, 30L * 24 * 60 * 60 * 1000, 6),
    LAST_6_MONTHS(R.string.period_6_months, 182L * 24 * 60 * 60 * 1000, 6)
}

val AnalyticsPeriod.label: String
    @Composable get() = stringResource(labelRes)
