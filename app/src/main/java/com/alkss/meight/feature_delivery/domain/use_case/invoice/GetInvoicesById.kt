package com.alkss.meight.feature_delivery.domain.use_case.invoice

import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository

class GetInvoicesById(
    private val repository: DeliveryRepository
) {
    suspend operator fun invoke(invoiceId: Int) = repository.getInvoiceById(invoiceId)
}
