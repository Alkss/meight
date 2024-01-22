package com.alkss.meight.feature_delivery.presentation.delivery

import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.presentation.invoice_detail.InvoiceDetailState

/**
 * Represents the UI state for the delivery feature.
 *
 * @property invoiceList The list of invoice details.
 * @property buttonEnabled Indicates whether the button is enabled or not.
 * @property apiError The error message from the API, if any.
 */
data class DeliveryUiState(
    val invoiceList: List<InvoiceDetailState> = emptyList(),
    val buttonEnabled: Boolean = true,
    val apiError: String? = null,
){
    companion object {
        /**
         * Maps the list of invoices to the corresponding DeliveryUiState object.
         *
         * @param invoiceList The list of invoices to be mapped.
         * @return The mapped DeliveryUiState object.
         */
        fun mapFrom(invoiceList : List<Invoice>): DeliveryUiState{
            return DeliveryUiState(
                invoiceList = invoiceList.map { InvoiceDetailState.mapFrom(it) }
            )
        }
    }
}