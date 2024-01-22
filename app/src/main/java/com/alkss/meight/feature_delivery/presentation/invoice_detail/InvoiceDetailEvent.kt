package com.alkss.meight.feature_delivery.presentation.invoice_detail

/**
 * Represents the events related to the invoice detail screen.
 */
sealed class InvoiceDetailEvent{
    /**
     * Event to mark the invoice as delivered.
     */
    data object MarkAsDelivered: InvoiceDetailEvent()

    /**
     * Event to get the list of invoices.
     */
    data object GetInvoices: InvoiceDetailEvent()

    /**
     * Event to mark the invoice as not delivered.
     */
    data object MarkAsNotDelivered: InvoiceDetailEvent()
}
