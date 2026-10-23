package com.example.tableo.data.repository.impl

import androidx.core.net.toUri
import com.example.tableo.data.repository.AuthRepository
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await

/** Zugriff auf die Benutzer-Anmeldung über Firebase Authentication. */
class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    // anonyme Gäste zählen in der App nicht als eingeloggt
    override fun currentUserId(): String? =
        firebaseAuth.currentUser?.takeUnless { it.isAnonymous }?.uid

    override suspend fun ensureSignedIn() {
        if (firebaseAuth.currentUser == null) {
            runCatching { firebaseAuth.signInAnonymously().await() }
        }
    }

    override fun currentUserEmail(): String? = firebaseAuth.currentUser?.email

    override fun currentUserName(): String? = firebaseAuth.currentUser?.displayName

    override fun currentUserPhotoUrl(): String? = firebaseAuth.currentUser?.photoUrl?.toString()

    override suspend fun updatePhotoUrl(photoUrl: String): Result<Unit> = runCatching {
        val request = userProfileChangeRequest { photoUri = photoUrl.toUri() }
        firebaseAuth.currentUser?.updateProfile(request)?.await()
    }

    override suspend fun register(email: String, password: String): Result<Unit> = runCatching {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun login(email: String, password: String): Result<Unit> = runCatching {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun updateDisplayName(name: String): Result<Unit> = runCatching {
        val request = userProfileChangeRequest { displayName = name }
        firebaseAuth.currentUser?.updateProfile(request)?.await()
    }

    override suspend fun updateEmail(newEmail: String): Result<Unit> = runCatching {
        firebaseAuth.currentUser?.verifyBeforeUpdateEmail(newEmail)?.await()
    }

    override suspend fun updatePassword(newPassword: String): Result<Unit> = runCatching {
        firebaseAuth.currentUser?.updatePassword(newPassword)?.await()
    }

    override suspend fun reauthenticate(currentPassword: String): Result<Unit> = runCatching {
        val user = firebaseAuth.currentUser ?: throw IllegalStateException("Nicht eingeloggt")
        val email = user.email ?: throw IllegalStateException("Keine E-Mail-Adresse hinterlegt")
        user.reauthenticate(EmailAuthProvider.getCredential(email, currentPassword)).await()
    }

    override suspend fun sendPasswordReset(email: String): Result<Unit> = runCatching {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    override suspend fun deleteAccount(): Result<Unit> = runCatching {
        val user = firebaseAuth.currentUser ?: throw IllegalStateException("Nicht eingeloggt")
        user.delete().await()
        ensureSignedIn()
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
        ensureSignedIn()
    }
}
