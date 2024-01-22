package com.alkss.meight.feature_delivery.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a vehicle used for delivery.
 *
 * @property plateNumber The plate number of the vehicle.
 * @property maxWeightCapacity The maximum weight capacity of the vehicle.
 */
@Entity
data class Vehicle(
    @PrimaryKey val plateNumber: String,
    val maxWeightCapacity: Double,
)
