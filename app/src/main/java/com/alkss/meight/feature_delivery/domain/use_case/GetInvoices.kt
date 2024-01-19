package com.alkss.meight.feature_delivery.domain.use_case

import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository

class GetInvoices(
    private val repository: DeliveryRepository
) {
    operator fun invoke() {
        repository.getInvoices()
    }
}