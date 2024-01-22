package com.alkss.meight.feature_delivery.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Invoice(
    @PrimaryKey val id: Int,
    val weight: Double,
    val destinationLat: Double,
    val destinationLong: Double,
    val observations: String,
    val vehiclePlateNumber: String,
    val order: Int = 0,
    val status: InvoiceStatus = InvoiceStatus.IN_PROGRESS
)

enum class InvoiceStatus {
    IN_PROGRESS,
    DELIVERED,
    NOT_DELIVERED
}
