package com.example.tableo.data.repository.impl

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

// Snapshot-Listener als Flow: meldet jede Änderung in Firestore, der Listener wird mit dem Flow beendet

/** Live-Ergebnisse einer Abfrage, z.B. alle offenen Bestellungen eines Restaurants. */
fun Query.asFlow(): Flow<QuerySnapshot> = callbackFlow {
    val listener = addSnapshotListener { snapshot, exception ->
        if (exception != null) {
            close(exception)
            return@addSnapshotListener
        }
        if (snapshot != null) trySend(snapshot)
    }
    awaitClose { listener.remove() }
}

/** Live-Stand eines einzelnen Dokuments, z.B. einer Bestellung beim Tracking. */
fun DocumentReference.asFlow(): Flow<DocumentSnapshot> = callbackFlow {
    val listener = addSnapshotListener { snapshot, exception ->
        if (exception != null) {
            close(exception)
            return@addSnapshotListener
        }
        if (snapshot != null) trySend(snapshot)
    }
    awaitClose { listener.remove() }
}
