package com.alkss.meight.feature_delivery.presentation.delivery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.alkss.meight.feature_delivery.domain.model.local.InvoiceStatus
import com.alkss.meight.feature_delivery.presentation.util.OnLifecycleEvent
import com.alkss.meight.feature_delivery.presentation.util.Screen
import com.alkss.meight.feature_delivery.presentation.util.getNext

@Composable
fun DeliveryScreen(
    navController: NavController,
    viewModel: DeliveryViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsState().value

    OnLifecycleEvent { owner, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.onEvent(DeliveryEvent.GetInvoicesByVehicle(uiState.invoiceList.first().vehiclePlateNumber))
            }

            else -> {}
        }
    }


    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.invoiceList.isEmpty()) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "You have no orders for the day",
                    style = MaterialTheme.typography.headlineLarge
                )
            } else {
                InvoiceListComponent(uiState, navController)
            }
        }

        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    onClick = { viewModel.onEvent(DeliveryEvent.GetInvoicesRequest) }
                ) {
                    Text(text = "Refresh Invoices")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    onClick = { navController.navigateUp() }
                ) {
                    Text(text = "Go Back")
                }
            }
        }
    }
}

@Composable
private fun InvoiceListComponent(
    uiState: DeliveryUiState,
    navController: NavController
) {
    var orderPosition = 0
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Your orders for the day",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(uiState.invoiceList) { invoiceId, invoiceDetail ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.secondary,
                            shape = MaterialTheme.shapes.extraSmall
                        )
                        .background(color = MaterialTheme.colorScheme.inverseOnSurface)
                        .padding(8.dp)
                ) {
                    Column(Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "#${++orderPosition}"
                            )
                            when (invoiceDetail.status) {
                                InvoiceStatus.DELIVERED -> {
                                    Text(
                                        text = "Delivered",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                InvoiceStatus.NOT_DELIVERED -> {
                                    Text(
                                        text = "Not Delivered",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                else -> {
                                    val nextInvoiceId =
                                        uiState.invoiceList.getNext(invoiceDetail)?.id
                                    ButtonRegion(navController, invoiceDetail.id, nextInvoiceId, invoiceDetail.vehiclePlateNumber)
                                }
                            }

                        }
                        Row {
                            Text(
                                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                text = "Observations: ${invoiceDetail.observations}"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ButtonRegion(
    navController: NavController,
    invoiceClickedId: Int,
    nextInvoiceId: Int? = null,
    vehicleId: String = ""
) {
    Button(
        modifier = Modifier
            .padding(end = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
        onClick = {
            navController.navigate(
                Screen.InvoiceDetailsScreen.route +
                        "?invoiceId=$invoiceClickedId" +
                        "&vehiclePlate=$vehicleId" +
                        "&nextInvoiceId=${nextInvoiceId ?: ""}"
            )
        }
    ) {
        Text(text = "Ver detalhes")
    }
}