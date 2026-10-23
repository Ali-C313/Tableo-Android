package com.example.tableo.data.repository.impl

import com.example.tableo.data.repository.StorageRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import kotlin.random.Random

/** Lädt Fotos zu Firebase Storage hoch. */
class StorageRepositoryImpl(
    private val storage: FirebaseStorage
) : StorageRepository {

    override suspend fun uploadMenuItemImage(restaurantId: String, imageBytes: ByteArray): Result<String> = runCatching {
        // eigener Dateiname pro Upload, damit sich zwei Bilder nie überschreiben
        val fileName = "${System.currentTimeMillis()}-${Random.nextInt(100_000)}.jpg"
        val ref = storage.reference.child("menuItems/$restaurantId/$fileName")
        ref.putBytes(imageBytes).await()
        ref.downloadUrl.await().toString()
    }

    override suspend fun uploadProfilePhoto(userId: String, imageBytes: ByteArray): Result<String> = runCatching {
        // ein Bild pro Konto, ein neues überschreibt das alte
        val ref = storage.reference.child("profilePhotos/$userId.jpg")
        ref.putBytes(imageBytes).await()
        ref.downloadUrl.await().toString()
    }

    override suspend fun deleteProfilePhoto(userId: String): Result<Unit> = runCatching {
        storage.reference.child("profilePhotos/$userId.jpg").delete().await()
        Unit
    }
}
