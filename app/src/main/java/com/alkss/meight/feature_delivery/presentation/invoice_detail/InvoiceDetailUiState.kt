package com.alkss.meight.feature_delivery.presentation.invoice_detail

import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.model.local.InvoiceStatus

/**
 * Represents the UI state of the invoice detail screen.
 *
 * @property currentInvoice The current invoice being displayed.
 * @property nextInvoice The next invoice in the list, if available.
 * @property availableWeight The available weight for the current invoice.
 */
data class InvoiceDetailUiState(
    val currentInvoice: InvoiceDetailState? = null,
    val nextInvoice: InvoiceDetailState? = null,
    val availableWeight: Double? = null
) {
    companion object {
        /**
         * Maps an [Invoice] object to [InvoiceDetailUiState].
         *
         * @param currentInvoice The current invoice to be mapped.
         * @param nextInvoice The next invoice to be mapped, if available.
         * @return The mapped [InvoiceDetailUiState] object.
         */
        fun mapFrom(currentInvoice: Invoice, nextInvoice: Invoice? = null): InvoiceDetailUiState =
            InvoiceDetailUiState(
                currentInvoice = InvoiceDetailState.mapFrom(currentInvoice),
                nextInvoice = nextInvoice?.let { InvoiceDetailState.mapFrom(it) },
            )
    }
}

/**
 * Represents the state of an invoice in the invoice detail screen.
 *
 * @property id The ID of the invoice.
 * @property weight The weight of the invoice.
 * @property destination The destination coordinates of the invoice.
 * @property observations Any observations related to the invoice.
 * @property vehiclePlateNumber The plate number of the vehicle associated with the invoice.
 * @property status The status of the invoice.
 */
data class InvoiceDetailState(
    val id: Int,
    val weight: Double,
    val destination: Pair<Double, Double>,
    val observations: String,
    val vehiclePlateNumber: String,
    val status: InvoiceStatus = InvoiceStatus.IN_PROGRESS
) {
    companion object {
        /**
         * Maps an [Invoice] object to [InvoiceDetailState].
         *
         * @param invoice The invoice to be mapped.
         * @return The mapped [InvoiceDetailState] object.
         */
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