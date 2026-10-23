package com.example.tableo.ui.customer.tracking

import com.example.tableo.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.example.tableo.data.enums.OrderStatus
import com.example.tableo.data.repository.mock.OrderRepositoryMock
import com.example.tableo.ui.components.TrackingMap
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.ui.res.stringResource

/**
 * Live-Tracking einer Lieferung. Sobald ein Fahrer die Bestellung hat, zeigt die Karte
 * Fahrer und Lieferadresse, darunter die geschätzte Ankunft. Hat der Fahrer vorher noch
 * andere Lieferungen, werden die Umwege in die Zeit eingerechnet.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackingScreen(
    onBackClick: () -> Unit = {},
    orderTrackingViewModel: OrderTrackingViewModel = koinViewModel()
) {
    val uiState by orderTrackingViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.tracking_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.common_back))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        when (val state = uiState) {
            is OrderTrackingUiState.Loading -> {
                Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }

            is OrderTrackingUiState.Error -> {
                Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.error_with_message, state.message), color = MaterialTheme.colorScheme.error)
                }
            }

            is OrderTrackingUiState.Success -> {
                val order = state.order
                val driverOnTheWay = order.status == OrderStatus.ANGENOMMEN || order.status == OrderStatus.UNTERWEGS

                Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    if (driverOnTheWay && order.driverLocation != null) {
                        TrackingMap(
                            driver = order.driverLocation,
                            destination = order.deliveryLocation,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (driverOnTheWay) stringResource(R.string.tracking_waiting_for_location)
                                else stringResource(R.string.tracking_no_driver_yet),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    TrackingInfoCard(order = order, stopsBefore = state.stopsBefore)
                }
            }
        }
    }
}

@Preview
@Composable
private fun OrderTrackingScreenPreview() {
    OrderTrackingScreen(
        orderTrackingViewModel = OrderTrackingViewModel(
            SavedStateHandle(mapOf("orderId" to "3")),
            OrderRepositoryMock()
        )
    )
}
