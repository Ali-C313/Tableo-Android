package com.example.tableo.data.repository.impl

import com.example.tableo.data.model.UserAccount
import com.example.tableo.data.remote.FirestoreCollections
import com.example.tableo.data.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/** Konto-Zusatzdaten in Firestore unter users/{userId}. */
class UserRepositoryImpl(
    firestore: FirebaseFirestore
) : UserRepository {

    private val userCollection = firestore.collection(FirestoreCollections.USERS)

    override suspend fun requestDeletion(userId: String): Result<Unit> = runCatching {
        userCollection.document(userId).set(UserAccount(deletionRequestedAt = System.currentTimeMillis())).await()
        Unit
    }

    override suspend fun deletionRequestedAt(userId: String): Long? {
        val snapshot = userCollection.document(userId).get().await()
        if (!snapshot.exists()) return null
        val requestedAt = snapshot.getLong("deletionRequestedAt") ?: 0
        return requestedAt.takeIf { it > 0 }
    }

    override suspend fun clearDeletion(userId: String): Result<Unit> = runCatching {
        userCollection.document(userId).delete().await()
        Unit
    }
}
