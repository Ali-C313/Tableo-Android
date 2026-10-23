package com.example.tableo.data.repository.impl

import com.example.tableo.data.model.Restaurant
import com.example.tableo.data.remote.FirestoreCollections
import com.example.tableo.data.repository.RestaurantRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

/** Zugriff auf die Restaurant-Daten in Firestore. */
class RestaurantRepositoryImpl(
    firestore: FirebaseFirestore
) : RestaurantRepository {

    private val restaurantCollection = firestore.collection(FirestoreCollections.RESTAURANTS)

    // meldet sich bei jeder Änderung in Firestore neu
    override fun getRestaurants(): Flow<List<Restaurant>> =
        restaurantCollection.asFlow().map { it.toRestaurants() }

    override suspend fun getRestaurantById(restaurantId: String): Restaurant? {
        if (restaurantId.isBlank()) return null
        return restaurantCollection.document(restaurantId).get().await().toRestaurantOrNull()
    }
}
