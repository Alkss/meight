package com.alkss.meight.feature_delivery.domain.use_case.invoice

/**
 * Represents a collection of use cases related to invoices.
 *
 * @property getInvoicesRequest The use case for retrieving invoices.
 * @property updateInvoice The use case for updating an invoice.
 * @property getInvoicesById The use case for retrieving invoices by ID.
 * @property getInvoicesByVehicle The use case for retrieving invoices by vehicle.
 * @property insertInvoiceList The use case for inserting a list of invoices.
 * @property updateInvoiceRequest The use case for updating an invoice request.
 */
data class InvoiceUseCases(
    val getInvoicesRequest: GetInvoicesRequest,
    val updateInvoice: UpdateInvoice,
    val getInvoicesById: GetInvoicesById,
    val getInvoicesByVehicle: GetInvoicesByVehicle,
    val insertInvoiceList: InsertInvoiceList,
    val updateInvoiceRequest: UpdateInvoiceRequest,
)
