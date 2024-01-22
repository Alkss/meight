package com.alkss.meight.feature_delivery.domain.use_case.vehicle

import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository

/**
 * Use case class responsible for inserting a list of vehicles into the repository.
 *
 * @param repository The repository used for inserting the vehicles.
 */
class InsertVehicles(
    private val repository: DeliveryRepository
) {
    /**
     * Inserts a list of vehicles into the repository.
     *
     * @param vehicles The list of vehicles to be inserted.
     */
    suspend operator fun invoke(vehicles: List<Vehicle>) {
        repository.insertAllVehicles(vehicles)
    }
}
