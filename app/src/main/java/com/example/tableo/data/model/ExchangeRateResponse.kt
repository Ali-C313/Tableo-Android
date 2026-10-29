package com.example.tableo.data.model

/** Antwort der Wechselkurs-API (frankfurter.app). */
data class ExchangeRateResponse(
    val rates: Map<String, Double> = emptyMap()
)
