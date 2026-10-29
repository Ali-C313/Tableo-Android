package com.example.tableo.util

import com.example.tableo.R
import androidx.compose.runtime.Composable
import com.example.tableo.data.enums.OrderType
import com.example.tableo.data.model.Order
import com.example.tableo.util.getString
import androidx.compose.ui.res.stringResource

/** Feste Standard-Zubereitungszeit, bis es echte Zeitangaben vom Restaurant gibt. */
private const val DefaultPreparationMinutes = 20

/** Grobe Fahrzeit bei Lieferung, kommt noch auf die Zubereitung drauf. */
private const val DefaultDeliveryMinutes = 20

/**
 * Geschätzter Zeitpunkt, zu dem das Essen da ist, als Text. Bei Abholung die gewählte Uhrzeit,
 * bei Lieferung Zubereitung plus Fahrzeit, sonst nur die Zubereitung.
 */
@Composable
fun Order.estimatedReadyTimeText(): String {
    if (orderType == OrderType.PICKUP && !pickupTime.isNullOrBlank()) {
        return stringResource(R.string.time_oclock, pickupTime)
    }
    val minutes = if (orderType == OrderType.DELIVERY) {
        DefaultPreparationMinutes + DefaultDeliveryMinutes
    } else {
        DefaultPreparationMinutes
    }
    val readyAt = createdAt + minutes * 60_000L
    return stringResource(R.string.time_approx_oclock, formatDateTime(readyAt, "HH:mm"))
}

/** Kurzer Zusatztext unter der geschätzten Zeit. */
@Composable
fun Order.estimatedHint(): String = when (orderType) {
    OrderType.PICKUP -> stringResource(R.string.estimate_hint_pickup)
    OrderType.DELIVERY -> stringResource(R.string.estimate_hint_delivery)
    OrderType.DINE_IN -> stringResource(R.string.estimate_hint_table, DefaultPreparationMinutes)
}

/** Bestellart als kurzer Text, z.B. "Tisch 7", "Abholung um 19:30 Uhr" oder "Lieferung". */
@Composable
fun Order.orderTypeLabel(): String = when (orderType) {
    OrderType.DINE_IN -> stringResource(R.string.order_type_table, tableNumber.toString())
    OrderType.PICKUP -> stringResource(R.string.order_type_pickup_at, pickupTime.orEmpty())
    OrderType.DELIVERY -> stringResource(R.string.order_type_delivery)
}

/** Wie [orderTypeLabel], aber außerhalb von Compose, z.B. für Benachrichtigungen. */
fun Order.orderTypeText(): String = when (orderType) {
    OrderType.DINE_IN -> getString(R.string.order_type_table, tableNumber.toString())
    OrderType.PICKUP -> getString(R.string.order_type_pickup_at, pickupTime.orEmpty())
    OrderType.DELIVERY -> getString(R.string.order_type_delivery)
}

/** Kurze Bestellnummer zum Vorlesen, z.B. "#A1B2C3". Kommt aus der Firestore-ID. */
fun Order.orderNumber(): String = "#" + id.take(6).uppercase()
