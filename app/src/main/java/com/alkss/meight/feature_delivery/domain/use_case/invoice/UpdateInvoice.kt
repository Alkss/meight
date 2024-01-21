package com.alkss.meight.feature_delivery.domain.use_case.invoice

import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository

class UpdateInvoice(
    private val repository: DeliveryRepository
) {
    suspend operator fun invoke(invoice: Invoice) {
        repository.updateInvoice(invoice)
    }
}
