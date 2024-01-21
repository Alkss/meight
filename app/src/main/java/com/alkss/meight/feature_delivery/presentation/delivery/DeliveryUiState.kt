package com.alkss.meight.feature_delivery.presentation.delivery

import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.presentation.invoice_detail.InvoiceDetailState
import com.alkss.meight.feature_delivery.presentation.invoice_detail.InvoiceDetailUiState

data class DeliveryUiState(
    val invoiceList: List<InvoiceDetailState> = emptyList()
){
    companion object {
        fun mapFrom(invoiceList : List<Invoice>): DeliveryUiState{
            return DeliveryUiState(
                invoiceList = invoiceList.map { InvoiceDetailState.mapFrom(it) }
            )
        }
    }
}