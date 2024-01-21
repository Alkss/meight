package com.alkss.meight.feature_delivery.presentation.invoice_detail

import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.model.local.InvoiceStatus

data class InvoiceDetailUiState(
    val currentInvoice: InvoiceDetailState? = null,
    val nextInvoice: InvoiceDetailState? = null,
    val availableWeight: Double? = null
) {
    companion object {
        fun mapFrom(currentInvoice: Invoice, nextInvoice: Invoice? = null): InvoiceDetailUiState =
            InvoiceDetailUiState(
                currentInvoice = InvoiceDetailState.mapFrom(currentInvoice),
                nextInvoice = nextInvoice?.let { InvoiceDetailState.mapFrom(it) },
            )
    }
}

data class InvoiceDetailState(
    val id: Int,
    val weight: Double,
    val destination: Pair<Double, Double>,
    val observations: String,
    val vehiclePlateNumber: String,
    val status: InvoiceStatus = InvoiceStatus.IN_PROGRESS
) {
    companion object {
        fun mapFrom(invoice: Invoice): InvoiceDetailState =
            InvoiceDetailState(
                id = invoice.id,
                weight = invoice.weight,
                destination = Pair(invoice.destinationLat, invoice.destinationLong),
                observations = invoice.observations,
                vehiclePlateNumber = invoice.vehiclePlateNumber,
                status = invoice.status
            )
    }
}