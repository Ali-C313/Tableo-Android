package com.example.tableo.data.enums

import com.example.tableo.R
import androidx.compose.runtime.Composable
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource

/** Auswahl oben auf der Startseite, wie bei Lieferando. */
enum class DeliveryMode(@StringRes val labelRes: Int) {
    ABHOLUNG(R.string.delivery_mode_pickup),
    LIEFERUNG(R.string.delivery_mode_delivery)
}

val DeliveryMode.label: String
    @Composable get() = stringResource(labelRes)
