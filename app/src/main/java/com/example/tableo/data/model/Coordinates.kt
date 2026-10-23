package com.example.tableo.data.model


/** Ein Punkt auf der Karte, z.B. der aktuelle Standort des Fahrers. */
data class Coordinates(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
