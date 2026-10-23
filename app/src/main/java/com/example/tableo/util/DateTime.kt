package com.example.tableo.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Formatiert einen Zeitstempel in der Zeitzone des Handys.
 *
 * @param pattern z.B. "dd.MM.yyyy, HH:mm" oder "HH:mm"
 */
fun formatDateTime(millis: Long, pattern: String): String =
    SimpleDateFormat(pattern, Locale.GERMANY).format(Date(millis))

/** Minuten seit Mitternacht, z.B. 14:30 -> 870. */
fun currentMinuteOfDay(): Int {
    val now = Calendar.getInstance()
    return now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE)
}
