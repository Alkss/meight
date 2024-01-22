package com.alkss.meight.feature_delivery.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents an invoice for a delivery.
 *
 * @property id The unique identifier of the invoice.
 * @property weight The weight of the delivery.
 * @property destinationLat The latitude of the delivery destination.
 * @property destinationLong The longitude of the delivery destination.
 * @property observations Additional observations for the delivery.
 * @property vehiclePlateNumber The plate number of the vehicle used for the delivery.
 * @property order The order of the invoice.
 * @property status The status of the invoice.
 */
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

/**
 * Represents the status of an invoice.
 */
enum class InvoiceStatus {
    IN_PROGRESS,
    DELIVERED,
    NOT_DELIVERED
}
