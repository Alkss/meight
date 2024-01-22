package com.alkss.meight.feature_delivery.domain.use_case.vehicle

import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case class for getting a list of vehicles.
 *
 * @param repository the delivery repository to retrieve the vehicles from
 */
class GetVehicles(
    private val repository: DeliveryRepository
) {
    /**
     * Invokes the use case and returns a flow of vehicles.
     *
     * @return a flow of vehicles
     */
    operator fun invoke() : Flow<List<Vehicle>> {
        return repository.getVehicles()
    }
}
