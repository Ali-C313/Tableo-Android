package com.example.tableo.data.repository.impl

import com.example.tableo.data.model.MenuItem
import com.example.tableo.data.model.Order
import com.example.tableo.data.model.Restaurant
import com.example.tableo.data.model.StaffProfile
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

// Die Dokument-ID steht nicht in den Daten selbst, deshalb wird sie nach dem Lesen dazugesetzt.
// Kaputte Dokumente (z.B. alte Testdaten) werden übersprungen statt die ganze Liste abstürzen zu lassen.

fun QuerySnapshot.toOrders(): List<Order> =
    documents.mapNotNull { doc -> runCatching { doc.toObject(Order::class.java)?.copy(id = doc.id) }.getOrNull() }

fun DocumentSnapshot.toOrderOrNull(): Order? =
    if (exists()) runCatching { toObject(Order::class.java)?.copy(id = id) }.getOrNull() else null

fun QuerySnapshot.toRestaurants(): List<Restaurant> =
    documents.mapNotNull { doc -> runCatching { doc.toObject(Restaurant::class.java)?.copy(id = doc.id) }.getOrNull() }

fun DocumentSnapshot.toRestaurantOrNull(): Restaurant? =
    if (exists()) runCatching { toObject(Restaurant::class.java)?.copy(id = id) }.getOrNull() else null

fun QuerySnapshot.toMenuItems(): List<MenuItem> =
    documents.mapNotNull { doc -> runCatching { doc.toObject(MenuItem::class.java)?.copy(id = doc.id) }.getOrNull() }

fun QuerySnapshot.toStaffProfiles(): List<StaffProfile> =
    documents.mapNotNull { doc -> runCatching { doc.toObject(StaffProfile::class.java)?.copy(id = doc.id) }.getOrNull() }

fun DocumentSnapshot.toStaffProfileOrNull(): StaffProfile? =
    if (exists()) runCatching { toObject(StaffProfile::class.java)?.copy(id = id) }.getOrNull() else null
