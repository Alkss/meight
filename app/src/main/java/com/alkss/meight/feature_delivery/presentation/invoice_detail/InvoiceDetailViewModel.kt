package com.alkss.meight.feature_delivery.presentation.invoice_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkss.meight.feature_delivery.domain.model.local.InvoiceStatus
import com.alkss.meight.feature_delivery.domain.use_case.invoice.InvoiceUseCases
import com.alkss.meight.feature_delivery.domain.use_case.vehicle.VehicleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for the Invoice Detail screen.
 * This class is responsible for handling user interactions and updating the UI state accordingly.
 *
 * @property vehicleUseCases The use cases for vehicle-related operations.
 * @property invoiceUseCases The use cases for invoice-related operations.
 */
@HiltViewModel
class InvoiceDetailViewModel @Inject constructor(
    private val vehicleUseCases: VehicleUseCases,
    private val invoiceUseCases: InvoiceUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(InvoiceDetailUiState())
    val state = _state.asStateFlow()

    private val _invoiceId: MutableStateFlow<String> = MutableStateFlow("")
    private val _nextInvoiceId: MutableStateFlow<String> = MutableStateFlow("")
    private val _vehicleId: MutableStateFlow<String> = MutableStateFlow("")

    /**
     * Updates the vehicle ID with the given value.
     * If the vehicle ID is not blank, it will be updated.
     *
     * @param vehicleId The new vehicle ID.
     */
    fun updateVehicleId(vehicleId: String) {
        if (vehicleId.isNotBlank()) {
            _vehicleId.update { vehicleId }
        }
    }

    /**
     * Updates the invoice ID with the given value.
     * If the invoice ID is not blank, it will be updated.
     *
     * @param invoiceId The new invoice ID.
     */
    fun updateInvoiceId(invoiceId: String) {
        if (invoiceId.isNotBlank()) {
            _invoiceId.update { invoiceId }
        }
    }

    /**
     * Updates the next invoice ID with the given value.
     * If the invoice ID is not blank, it will be updated.
     *
     * @param invoiceId The new invoice ID.
     */
    fun updateNextInvoiceId(invoiceId: String) {
        if (invoiceId.isNotBlank()) {
            _nextInvoiceId.update { invoiceId }
        }
    }

    /**
     * Handles the given [event] and updates the UI state accordingly.
     *
     * @param event The event to handle.
     */
    fun onEvent(event: InvoiceDetailEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            val invoice = invoiceUseCases.getInvoicesById(_invoiceId.value.toInt())
            when (event) {
                InvoiceDetailEvent.MarkAsDelivered -> {
                    val newInvoice = invoice!!.copy(status = InvoiceStatus.DELIVERED)
                    when (invoiceUseCases.updateInvoiceRequest(newInvoice)) {
                        true -> {
                            invoiceUseCases.updateInvoice(invoice = newInvoice)
                            getRemainingWeight()
                            _state.update {
                                it.copy(
                                    currentInvoice = InvoiceDetailState.mapFrom(newInvoice)
                                )
                            }
                        }

                        false -> {
                            //do nothing... maybe add a logger to inform the user that the request failed
                        }
                    }
                }

                InvoiceDetailEvent.MarkAsNotDelivered -> {
                    val newInvoice = invoice!!.copy(status = InvoiceStatus.NOT_DELIVERED)
                    when (invoiceUseCases.updateInvoiceRequest(newInvoice)) {
                        true -> {
                            invoiceUseCases.updateInvoice(invoice = newInvoice)
                            getRemainingWeight()
                            _state.update {
                                it.copy(
                                    currentInvoice = InvoiceDetailState.mapFrom(newInvoice)
                                )
                            }
                        }

                        false -> {
                            //do nothing... maybe add a logger to inform the user that the request failed
                        }
                    }
                }

                InvoiceDetailEvent.GetInvoices -> {
                    invoiceUseCases.getInvoicesById.invoke(_invoiceId.value.toInt())
                        ?.also { invoice ->
                            _state.update {
                                it.copy(
                                    currentInvoice = InvoiceDetailState.mapFrom(
                                        invoice
                                    )
                                )
                            }
                        }

                    if (_nextInvoiceId.value.isNotBlank()) {
                        invoiceUseCases.getInvoicesById.invoke(_nextInvoiceId.value.toInt())
                            ?.also { invoice ->
                                _state.update {
                                    it.copy(
                                        nextInvoice = InvoiceDetailState.mapFrom(
                                            invoice
                                        )
                                    )
                                }
                            }
                    }

                    getRemainingWeight()
                }
            }
        }
    }

    /**
     * Calculates the remaining weight for the vehicle and updates the UI state.
     */
    private fun getRemainingWeight() {
        viewModelScope.launch(Dispatchers.IO) {
            val invoices = invoiceUseCases.getInvoicesByVehicle(_vehicleId.value)
            val vehicle =
                vehicleUseCases.getVehicles()
                    .map { vehicleList -> vehicleList.firstOrNull { it.plateNumber == _vehicleId.value } }

            var invoiceWeightAtVehicle = 0.0

            val filteredList =
                invoices?.map { invoiceList -> invoiceList.filter { it.status != InvoiceStatus.DELIVERED } }

            if (filteredList?.firstOrNull()?.isEmpty() != true) {
                invoiceWeightAtVehicle = filteredList?.map { invoiceList ->
                    invoiceList.map { it.weight }.reduce { acc, d -> acc + d }
                }!!.first()
            }

            val availableWeight =
                vehicle.firstOrNull()?.maxWeightCapacity?.minus(invoiceWeightAtVehicle)

            val availableWeightFormatted = String.format("%.2f", availableWeight).toDouble()
            _state.update {
                it.copy(availableWeight = availableWeightFormatted)
            }
        }
    }
}