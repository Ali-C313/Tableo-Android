package com.example.tableo.data.repository.impl

import com.example.tableo.data.enums.OrderStatus
import com.example.tableo.data.enums.OrderType
import com.example.tableo.data.model.Coordinates
import com.example.tableo.data.model.Order
import com.example.tableo.data.remote.FirestoreCollections
import com.example.tableo.data.repository.OrderRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

/** Zugriff auf die Bestellungen in Firestore. */
class OrderRepositoryImpl(
    private val firestore: FirebaseFirestore
) : OrderRepository {

    private val orderCollection = firestore.collection(FirestoreCollections.ORDERS)

    // ERLEDIGT, STORNIERT und der alte Status BEZAHLT zählen als abgeschlossen
    private val doneStatuses = listOf(OrderStatus.ERLEDIGT.name, OrderStatus.BEZAHLT.name, OrderStatus.STORNIERT.name)

    override fun getActiveOrders(restaurantId: String): Flow<List<Order>> =
        orderCollection
            .whereEqualTo("restaurantId", restaurantId)
            .whereNotIn("status", doneStatuses)
            .asFlow()
            .map { it.toOrders() }

    override fun getOrderHistory(restaurantId: String): Flow<List<Order>> =
        orderCollection
            .whereEqualTo("restaurantId", restaurantId)
            .whereIn("status", doneStatuses)
            .asFlow()
            .map { it.toOrders() }

    override fun getOrdersForUser(userId: String): Flow<List<Order>> =
        orderCollection
            .whereEqualTo("userId", userId)
            .asFlow()
            .map { it.toOrders() }

    override fun getDeliveryOrders(restaurantId: String): Flow<List<Order>> =
        // nur Gleichheits-Filter, braucht keinen extra Index. Status wird im ViewModel gefiltert
        orderCollection
            .whereEqualTo("restaurantId", restaurantId)
            .whereEqualTo("orderType", OrderType.DELIVERY.name)
            .asFlow()
            .map { it.toOrders() }

    override fun getOrder(orderId: String): Flow<Order?> =
        orderCollection.document(orderId).asFlow().map { it.toOrderOrNull() }

    override suspend fun placeOrder(order: Order): Result<String> = runCatching {
        // add legt das Dokument mit automatischer ID an
        orderCollection.add(order).await().id
    }

    override suspend fun updateOrderStatus(orderId: String, status: OrderStatus): Result<Unit> = runCatching {
        orderCollection.document(orderId).update("status", status.name).await()
        Unit
    }

    override suspend fun updateDriverLocation(orderIds: List<String>, location: Coordinates): Result<Unit> = runCatching {
        val value = mapOf("latitude" to location.latitude, "longitude" to location.longitude)
        val batch = firestore.batch()
        orderIds.forEach { id -> batch.update(orderCollection.document(id), "driverLocation", value) }
        batch.commit().await()
        Unit
    }

    override suspend fun acceptDelivery(
        orderId: String,
        driverName: String,
        tourPosition: Int,
        status: OrderStatus
    ): Result<Unit> = runCatching {
        orderCollection.document(orderId).update(
            mapOf(
                "driverName" to driverName,
                "tourPosition" to tourPosition,
                "status" to status.name
            )
        ).await()
        Unit
    }

    override suspend fun cancelOrder(orderId: String, byRestaurant: Boolean): Result<Unit> = runCatching {
        val docRef = orderCollection.document(orderId)
        // Transaktion, damit der Status beim Stornieren nicht gleichzeitig weitergeschaltet wird
        val cancelled = firestore.runTransaction { transaction ->
            val statusName = transaction.get(docRef).getString("status")
            val status = OrderStatus.entries.find { it.name == statusName }
            val allowed = if (byRestaurant) status?.canRestaurantCancel == true else status == OrderStatus.OFFEN
            if (allowed) {
                transaction.update(
                    docRef,
                    mapOf("status" to OrderStatus.STORNIERT.name, "cancelledByRestaurant" to byRestaurant)
                )
            }
            allowed
        }.await()
        if (!cancelled) {
            error(
                if (byRestaurant) "Die Bestellung ist schon unterwegs und kann nicht mehr storniert werden"
                else "Die Bestellung wird schon zubereitet und kann nicht mehr storniert werden"
            )
        }
    }

    override suspend fun updateTourPositions(positions: Map<String, Int>): Result<Unit> = runCatching {
        val batch = firestore.batch()
        positions.forEach { (id, position) -> batch.update(orderCollection.document(id), "tourPosition", position) }
        batch.commit().await()
        Unit
    }
}
