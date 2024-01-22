package com.alkss.meight.feature_delivery.domain.use_case.invoice

import com.alkss.meight.feature_delivery.data.remote.manager.ServerMockManager
import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import kotlinx.coroutines.flow.Flow

/**
 * Represents a request to get all invoices.
 *
 * @property mockManager The server mock manager.
 */
class GetInvoicesRequest(
    private val mockManager: ServerMockManager
) {
    /**
     * Executes the request and returns a flow of invoices.
     *
     * @return A flow of invoices.
     */
    operator fun invoke(): Flow<List<Invoice>> {
        return mockManager.getAllInvoices()
    }
}