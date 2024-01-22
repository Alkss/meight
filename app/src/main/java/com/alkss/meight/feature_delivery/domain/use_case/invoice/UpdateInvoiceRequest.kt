package com.alkss.meight.feature_delivery.domain.use_case.invoice

import com.alkss.meight.feature_delivery.data.remote.manager.ServerMockManager
import com.alkss.meight.feature_delivery.domain.model.local.Invoice

/**
 * Represents a use case for updating an invoice.
 *
 * @property serverMockManager The server mock manager used for updating the invoice status.
 */
class UpdateInvoiceRequest(
    private val serverMockManager: ServerMockManager
) {
    /**
     * Updates the status of the given invoice.
     *
     * @param invoice The invoice to be updated.
     * @return `true` if the invoice status was successfully updated, `false` otherwise.
     */
    operator fun invoke(invoice: Invoice): Boolean {
        return serverMockManager.updateInvoiceStatus(invoice)
    }
}
