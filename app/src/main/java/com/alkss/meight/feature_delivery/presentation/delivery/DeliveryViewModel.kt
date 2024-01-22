package com.alkss.meight.feature_delivery.presentation.delivery

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkss.meight.core.HereAPI
import com.alkss.meight.core.NetworkResult
import com.alkss.meight.feature_delivery.data.remote.manager.HereApiManager
import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.use_case.invoice.InvoiceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for the Delivery feature.
 * This class is responsible for handling the business logic and state management related to delivery.
 *
 * @param invoiceUseCases The use cases for invoice operations.
 * @param hereApiManager The manager for Here API operations.
 */
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val invoiceUseCases: InvoiceUseCases,
    private val hereApiManager: HereApiManager
) : ViewModel() {

    // State flow for the UI state of the delivery feature
    private val _state: MutableStateFlow<DeliveryUiState> =
        MutableStateFlow(DeliveryUiState())

    val state = _state.asStateFlow()

    // State flow for the vehicle ID
    private val _vehicleId: MutableStateFlow<String> = MutableStateFlow("")

    init {
        // Initialize the ViewModel
        viewModelScope.launch(Dispatchers.IO) {
            invoiceUseCases.getInvoicesByVehicle.invoke(_vehicleId.value)
                ?.also { invoiceListFlow ->
                    invoiceListFlow.collectLatest { invoiceList ->
                        _state.update { DeliveryUiState.mapFrom(invoiceList) }
                    }
                }
        }
    }

    /**
     * Handles the events triggered in the delivery feature.
     *
     * @param event The delivery event to handle.
     */
    fun onEvent(event: DeliveryEvent) {
        when (event) {
            is DeliveryEvent.GetInvoicesByVehicle -> {
                viewModelScope.launch(Dispatchers.IO) {
                    invoiceUseCases.getInvoicesByVehicle.invoke(_vehicleId.value)
                        ?.also { invoiceListFlow ->
                            invoiceListFlow.collectLatest { invoiceList ->
                                _state.update { DeliveryUiState.mapFrom(invoiceList) }
                            }
                        }
                }
            }

            is DeliveryEvent.GetInvoicesRequest -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val invoiceList = invoiceUseCases.getInvoicesRequest.invoke().first()
                    getInvoices(invoiceList = invoiceList)
                }
            }
        }
    }

    /**
     * Updates the vehicle ID.
     *
     * @param vehicleId The new vehicle ID.
     */
    fun updateVehicleId(vehicleId: String) {
        _vehicleId.update { vehicleId }
    }

    /**
     * Retrieves the invoices and performs distance calculation using Here API.
     *
     * @param invoiceList The list of invoices to process.
     */
    private suspend fun getInvoices(invoiceList: List<Invoice>) {
        Log.d("DeliveryViewModel", "getInvoices: ${_vehicleId.value}")
        _state.update { it.copy(buttonEnabled = false) }
        val filteredList = invoiceList.filter { it.vehiclePlateNumber == _vehicleId.value }
        when (val orderedList = hereApiManager.calculateDistance(
            origin = HereAPI.startingPoint,
            invoiceList = filteredList.toMutableList()
        )) {
            is NetworkResult.Error -> {
                _state.update {
                    it.copy(
                        apiError = orderedList.exception.message,
                        buttonEnabled = true
                    )
                }
                return
            }

            is NetworkResult.Success -> {
                invoiceUseCases.insertInvoiceList.invoke(orderedList.data)
                _state.update { DeliveryUiState.mapFrom(orderedList.data) }
                _state.update {
                    it.copy(apiError = null)

                }
            }
        }
        _state.update { it.copy(buttonEnabled = true) }
    }
}
