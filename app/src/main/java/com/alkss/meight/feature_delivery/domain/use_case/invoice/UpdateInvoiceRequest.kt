package com.alkss.meight.feature_delivery.domain.use_case.invoice

import com.alkss.meight.feature_delivery.data.remote.manager.ServerMockManager
import com.alkss.meight.feature_delivery.domain.model.local.Invoice

class UpdateInvoiceRequest(
    private val serverMockManager: ServerMockManager
) {
    operator fun invoke(invoice: Invoice): Boolean {
        return serverMockManager.updateInvoiceStatus(invoice)
    }
}
