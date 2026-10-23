package com.example.tableo.data.enums

import com.example.tableo.R
import androidx.compose.runtime.Composable
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource

/** Bezahlarten im Warenkorb. Es wird noch nicht echt bezahlt, die Auswahl wird nur mit der Bestellung gespeichert. */
enum class PaymentMethod(@StringRes val labelRes: Int) {
    BAR(R.string.payment_cash),
    PAYPAL(R.string.payment_paypal),
    APPLE_PAY(R.string.payment_apple_pay),
    GOOGLE_PAY(R.string.payment_google_pay),
    KLARNA(R.string.payment_klarna),
    KREDITKARTE(R.string.payment_card)
}

val PaymentMethod.label: String
    @Composable get() = stringResource(labelRes)
