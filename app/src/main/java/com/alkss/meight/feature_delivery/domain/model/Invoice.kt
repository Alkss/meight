package com.alkss.meight.feature_delivery.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Invoice(
    @PrimaryKey val id: Int,
    val weight: Double,
    val destination: String,
    val observations: String,
    val vehiclePlateNumber: String,
    val status: InvoiceStatus = InvoiceStatus.PENDING
)

enum class InvoiceStatus {
    PENDING,
    DELIVERED,
    CANCELED
}
