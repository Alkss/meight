package com.alkss.meight.feature_delivery.domain.use_case.vehicle

import com.alkss.meight.feature_delivery.data.remote.manager.ServerMockManager
import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
import kotlinx.coroutines.flow.Flow

class RefreshVehicles(
    private val mockManager: ServerMockManager
) {
    operator fun invoke(): Flow<List<Vehicle>> {
       return mockManager.getAllVehicles()
    }
}