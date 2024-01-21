package com.alkss.meight.feature_delivery.domain.use_case.invoice

import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository

class GetInvoicesByVehicle(
    private val repository: DeliveryRepository
) {
    operator fun invoke(vehicleId: String) = repository.getInvoiceListByPlateNumber(vehicleId)
}
