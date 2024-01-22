package com.alkss.meight.feature_delivery.domain.use_case.vehicle

import com.alkss.meight.feature_delivery.data.remote.manager.ServerMockManager
import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
import kotlinx.coroutines.flow.Flow

/**
 * Use case class for refreshing the list of vehicles.
 *
 * This class is responsible for retrieving the latest list of vehicles from the server.
 * It uses the [mockManager] to fetch the vehicles and returns them as a [Flow] of [List] of [Vehicle].
 */
class RefreshVehicles(
    private val mockManager: ServerMockManager
) {
    /**
     * Invokes the use case and returns a [Flow] of [List] of [Vehicle].
     *
     * @return a [Flow] of [List] of [Vehicle] representing the latest list of vehicles.
     */
    operator fun invoke(): Flow<List<Vehicle>> {
       return mockManager.getAllVehicles()
    }
}