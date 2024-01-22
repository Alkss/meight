package com.alkss.meight.feature_delivery.domain.use_case.invoice

import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository

/**
 * Use case class for retrieving an invoice by its ID.
 *
 * @param repository the delivery repository used for retrieving the invoice
 */
class GetInvoicesById(
    private val repository: DeliveryRepository
) {
    /**
     * Retrieves an invoice by its ID.
     *
     * @param invoiceId the ID of the invoice to retrieve
     * @return the retrieved invoice
     */
    suspend operator fun invoke(invoiceId: Int) = repository.getInvoiceById(invoiceId)
}
