package com.alkss.meight.feature_delivery.domain.use_case.invoice

data class InvoiceUseCases(
    val getInvoicesRequest: GetInvoicesRequest,
    val updateInvoice: UpdateInvoice,
    val getInvoicesById: GetInvoicesById,
    val getInvoicesByVehicle: GetInvoicesByVehicle,
    val insertInvoiceList: InsertInvoiceList,
    val updateInvoiceRequest: UpdateInvoiceRequest,
)
