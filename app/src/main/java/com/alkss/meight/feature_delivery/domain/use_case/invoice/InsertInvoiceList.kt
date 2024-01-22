package com.alkss.meight.feature_delivery.domain.use_case.invoice

import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository

/**
 * Use case class for inserting a list of invoices into the repository.
 *
 * @param repository The delivery repository to perform the insertion.
 */
class InsertInvoiceList(
    private val repository: DeliveryRepository
) {
    /**
     * Inserts a list of invoices into the repository.
     *
     * @param invoiceList The list of invoices to be inserted.
     */
    suspend operator fun invoke(invoiceList: List<Invoice>) {
        repository.insertAllInvoices(invoiceList)
    }
}
