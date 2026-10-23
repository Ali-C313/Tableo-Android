package com.example.tableo.data.repository.impl

import com.example.tableo.data.model.MenuItem
import com.example.tableo.data.remote.FirestoreCollections
import com.example.tableo.data.repository.MenuRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

/** Zugriff auf die Speisekarten-Einträge in Firestore. */
class MenuRepositoryImpl(
    firestore: FirebaseFirestore
) : MenuRepository {

    private val menuCollection = firestore.collection(FirestoreCollections.MENU_ITEMS)

    override fun getMenuItems(restaurantId: String): Flow<List<MenuItem>> =
        menuCollection
            .whereEqualTo("restaurantId", restaurantId)
            .asFlow()
            .map { it.toMenuItems() }

    override suspend fun saveMenuItem(menuItem: MenuItem): Result<Unit> = runCatching {
        if (menuItem.id.isBlank()) {
            menuCollection.add(menuItem).await()
        } else {
            menuCollection.document(menuItem.id).set(menuItem).await()
        }
        Unit
    }

    override suspend fun setAvailability(menuItemId: String, isAvailable: Boolean): Result<Unit> = runCatching {
        menuCollection.document(menuItemId).update("available", isAvailable).await()
        Unit
    }

    override suspend fun deleteMenuItem(menuItemId: String): Result<Unit> = runCatching {
        menuCollection.document(menuItemId).delete().await()
        Unit
    }
}
