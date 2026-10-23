package com.example.tableo.util

import com.example.tableo.R
import androidx.annotation.DrawableRes

/**
 * Passendes Bild zur Küche aus Firestore, z.B. "Burger" oder "Frühstück".
 * Groß-/Kleinschreibung und Leerzeichen sind egal, unbekannte Küchen bekommen null.
 */
@DrawableRes
fun cuisineImage(cuisine: String): Int? =
    when (cuisine.trim().lowercase().replace(" ", "").replace("-", "")) {
        "burger" -> R.drawable.cuisine_burger
        "fastfood" -> R.drawable.cuisine_fastfood
        "kaffee", "café", "cafe", "coffee" -> R.drawable.cuisine_kaffee
        "frühstück", "fruehstueck", "brunch" -> R.drawable.cuisine_fruehstueck
        "shisha" -> R.drawable.cuisine_shisha
        "indisch" -> R.drawable.cuisine_indisch
        "getränke", "getraenke", "drinks" -> R.drawable.cuisine_getraenke
        "pizza" -> R.drawable.cuisine_pizza
        "pasta" -> R.drawable.cuisine_pasta
        "sushi" -> R.drawable.cuisine_sushi
        "asiatisch" -> R.drawable.cuisine_asiatisch
        else -> null
    }
