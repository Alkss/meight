package com.alkss.meight.feature_delivery.presentation.invoice_detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.alkss.meight.feature_delivery.domain.model.local.InvoiceStatus
import com.alkss.meight.feature_delivery.presentation.util.OnLifecycleEvent

@Composable
fun InvoiceDetailsScreen(
    navController: NavController,
    viewModel: InvoiceDetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsState().value

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.onEvent(InvoiceDetailEvent.GetInvoices)
            }

            else -> { /* do nothihng */
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Selected Invoice",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Column(Modifier.padding(16.dp)) {
                uiState.currentInvoice?.destination?.let {
                    GeoButton(
                        modifier = Modifier.fillMaxWidth(),
                        latitude = it.first, longitude = it.second
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = "Weight: ${uiState.currentInvoice?.weight}"
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = "Observations: ${uiState.currentInvoice?.observations}"
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = "Vehicle Plate Number: ${uiState.currentInvoice?.vehiclePlateNumber}"
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = "Status: ${uiState.currentInvoice?.status}"
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    enabled = uiState.currentInvoice?.status != InvoiceStatus.DELIVERED,
                    modifier = Modifier.weight(10f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
                    onClick = {
                        viewModel.onEvent(InvoiceDetailEvent.MarkAsDelivered)
                    }
                ) {
                    Text(text = "DELIVERED")
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    enabled = uiState.currentInvoice?.status != InvoiceStatus.NOT_DELIVERED,
                    modifier = Modifier.weight(10f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    onClick = {
                        viewModel.onEvent(InvoiceDetailEvent.MarkAsNotDelivered)
                    }
                ) {
                    Text(text = "NOT DELIVERED")
                }
            }

            NextInvoice(uiState)
        }

        uiState.availableWeight?.let {
            Card(Modifier.fillMaxWidth()) {
                Text(
                    text = "Available weight on vehicle: $it kg",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun NextInvoice(uiState: InvoiceDetailUiState) {
    uiState.nextInvoice?.let {
        Spacer(modifier = Modifier.height(12.dp))
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Next Invoice",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                GeoButton(latitude = it.destination.first, longitude = it.destination.second)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    text = "Weight: ${it.weight}",
                    modifier = Modifier.weight(2f)
                )
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .width(4.dp)
                )
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    text = "Observation: ${it.observations}",
                    modifier = Modifier.weight(5f)
                )
            }
        }
    }
}

@Composable
fun GeoButton(modifier: Modifier = Modifier, latitude: Double, longitude: Double) {
    val context = LocalContext.current
    TextButton(modifier = modifier, onClick = { openGeoLocation(context, latitude, longitude) }) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Open Location",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

fun openGeoLocation(context: Context, latitude: Double, longitude: Double) {
    val openGeoIntent =
        Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=$latitude,$longitude")).apply {
            setPackage("com.google.android.apps.maps")
        }
    context.startActivity(openGeoIntent)
}