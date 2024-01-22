package com.alkss.meight.feature_delivery.presentation.delivery

import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.presentation.invoice_detail.InvoiceDetailState

data class DeliveryUiState(
    val invoiceList: List<InvoiceDetailState> = emptyList(),
    val buttonEnabled: Boolean = true,
    val apiError: String? = null,
){
    companion object {
        fun mapFrom(invoiceList : List<Invoice>): DeliveryUiState{
            return DeliveryUiState(
                invoiceList = invoiceList.map { InvoiceDetailState.mapFrom(it) }
            )
        }
    }
}