package com.alkss.meight.feature_delivery.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Vehicle(
    @PrimaryKey val plateNumber: String,
    val maxWeightCapacity: Double,
)
