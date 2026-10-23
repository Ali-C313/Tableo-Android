package com.example.tableo.ui.guest.restaurantList

import com.example.tableo.R
import com.example.tableo.util.getString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tableo.data.repository.AuthRepository
import com.example.tableo.data.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Lädt alle Restaurants live aus Firestore und kennt den Namen für die Begrüßung. */
class RestaurantListViewModel(
    private val restaurantRepository: RestaurantRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val isLoggedIn: Boolean get() = authRepository.currentUserId() != null

    /** Gleicher Name wie im Konto: erst Anzeigename, sonst E-Mail vor dem @. */
    val displayName: String?
        get() = authRepository.currentUserName()?.takeIf { it.isNotBlank() }
            ?: authRepository.currentUserEmail()?.substringBefore("@")

    private val _uiState = MutableStateFlow<RestaurantListUiState>(RestaurantListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        observeRestaurants()
    }

    // hört dauerhaft auf Firestore-Änderungen (Live-Liste)
    private fun observeRestaurants() {
        viewModelScope.launch {
            restaurantRepository.getRestaurants()
                .catch { e ->
                    _uiState.update { RestaurantListUiState.Error(e.message ?: getString(R.string.restaurants_error_load)) }
                }
                .collect { restaurants ->
                    _uiState.update { RestaurantListUiState.Success(restaurants) }
                }
        }
    }
}
