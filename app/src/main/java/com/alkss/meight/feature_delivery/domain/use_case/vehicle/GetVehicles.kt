package com.alkss.meight.feature_delivery.domain.use_case.vehicle

import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository
import kotlinx.coroutines.flow.Flow

class GetVehicles(
    private val repository: DeliveryRepository
) {
    operator fun invoke() : Flow<List<Vehicle>> {
        return repository.getVehicles()
    }
}
