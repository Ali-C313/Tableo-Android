package com.example.tableo.util

import com.example.tableo.R
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.example.tableo.util.getString

/**
 * Übersetzt technische Firebase-Auth-Fehler in verständliche Meldungen in der eingestellten Sprache.
 * Unterschieden wird nach der Klasse der Firebase-Exception.
 */
fun mapFirebaseAuthError(e: Throwable?): String = when (e) {
    // muss vor InvalidCredentials stehen, weil es davon erbt
    is FirebaseAuthWeakPasswordException -> getString(R.string.auth_error_weak_password)
    is FirebaseAuthInvalidCredentialsException -> getString(R.string.auth_error_wrong_credentials)
    is FirebaseAuthInvalidUserException -> getString(R.string.auth_error_no_account)
    is FirebaseAuthUserCollisionException -> getString(R.string.auth_error_email_taken)
    is FirebaseAuthRecentLoginRequiredException -> getString(R.string.auth_error_recent_login)
    is FirebaseAuthEmailException -> getString(R.string.auth_error_invalid_email)
    else -> e?.message ?: getString(R.string.common_error_generic)
}
