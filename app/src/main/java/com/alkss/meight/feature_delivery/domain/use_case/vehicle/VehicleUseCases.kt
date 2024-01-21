package com.alkss.meight.feature_delivery.domain.use_case.vehicle

data class VehicleUseCases(
    val getVehicles: GetVehicles,
    val refreshTrucks: RefreshVehicles,
    val insertVehicles: InsertVehicles
)