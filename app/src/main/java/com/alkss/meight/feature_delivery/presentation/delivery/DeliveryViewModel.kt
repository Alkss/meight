package com.alkss.meight.feature_delivery.presentation.delivery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkss.meight.feature_delivery.data.remote.manager.HereApiManager
import com.alkss.meight.feature_delivery.domain.use_case.invoice.InvoiceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val invoiceUseCases: InvoiceUseCases,
    private val hereApiManager: HereApiManager
) : ViewModel() {

    private val _state: MutableStateFlow<DeliveryUiState> =
        MutableStateFlow(DeliveryUiState())

    val state = _state.asStateFlow()

    private val _vehicleId: MutableStateFlow<String> = MutableStateFlow("")

    private var getInvoiceJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getInvoices()
        }
    }

    fun onEvent(event: DeliveryEvent) {
        when (event) {
            is DeliveryEvent.GetInvoicesByVehicle -> {
                viewModelScope.launch(Dispatchers.IO) {
                    getInvoices()
                }
            }

            is DeliveryEvent.GetInvoicesRequest -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val invoiceList = invoiceUseCases.getInvoicesRequest.invoke()
                    invoiceUseCases.insertInvoiceList.invoke(invoiceList.first())
                    getInvoices()
                }
            }
        }
    }

    fun updateVehicleId(vehicleId: String) {
        _vehicleId.update { vehicleId }
    }

    private suspend fun getInvoices() {
        getInvoiceJob?.cancel()
        getInvoiceJob =
            invoiceUseCases.getInvoicesByVehicle.invoke(_vehicleId.value)
                ?.also { invoiceListFlow ->
                    invoiceListFlow.collectLatest { invoiceList ->
                        _state.update { DeliveryUiState.mapFrom(invoiceList) }
                    }
                }?.launchIn(viewModelScope)
    }
}
