package com.alkss.meight.feature_delivery.domain.use_case.invoice

import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository

/**
 * Use case class for updating an invoice.
 *
 * @param repository the delivery repository used for updating the invoice
 */
class UpdateInvoice(
    private val repository: DeliveryRepository
) {
    /**
     * Updates the specified invoice.
     *
     * @param invoice the invoice to be updated
     */
    suspend operator fun invoke(invoice: Invoice) {
        repository.updateInvoice(invoice)
    }
}
