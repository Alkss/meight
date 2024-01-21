package com.alkss.meight.feature_delivery.domain.use_case.invoice

import com.alkss.meight.feature_delivery.data.remote.manager.ServerMockManager
import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import kotlinx.coroutines.flow.Flow

class GetInvoicesRequest(
    private val mockManager: ServerMockManager
) {
    operator fun invoke(): Flow<List<Invoice>> {
        return mockManager.getAllInvoices()
    }
}