package com.alkss.meight.feature_delivery.presentation.invoice_detail

sealed class InvoiceDetailEvent{
    data object MarkAsDelivered: InvoiceDetailEvent()

    data object GetInvoices: InvoiceDetailEvent()

    data object MarkAsNotDelivered: InvoiceDetailEvent()
}
