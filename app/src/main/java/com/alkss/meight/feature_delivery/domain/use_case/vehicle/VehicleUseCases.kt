package com.alkss.meight.feature_delivery.domain.use_case.vehicle

/**
 * Represents a collection of vehicle use cases.
 *
 * @property getVehicles The use case for retrieving vehicles.
 * @property refreshTrucks The use case for refreshing vehicles.
 * @property insertVehicles The use case for inserting vehicles.
 */
data class VehicleUseCases(
    val getVehicles: GetVehicles,
    val refreshTrucks: RefreshVehicles,
    val insertVehicles: InsertVehicles
)