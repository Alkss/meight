package com.alkss.meight.feature_delivery.domain.use_case.vehicle

import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository

class InsertVehicles(
    private val repository: DeliveryRepository
) {
    suspend operator fun invoke(vehicles: List<Vehicle>) {
        repository.insertAllVehicles(vehicles)
    }
}
