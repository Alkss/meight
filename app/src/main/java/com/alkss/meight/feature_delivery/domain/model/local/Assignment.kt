package com.alkss.meight.feature_delivery.domain.model.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Represents an assignment in the delivery domain.
 *
 * An assignment is a task that associates an invoice with a vehicle for delivery.
 *
 * @property id The unique identifier of the assignment.
 * @property invoiceId The ID of the associated invoice.
 * @property vehiclePlateNumber The plate number of the associated vehicle.
 * @property date The date of the assignment.
 * @property availableWeight The available weight for delivery.
 */
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Invoice::class,
            parentColumns = ["id"],
            childColumns = ["invoiceId"]
        ),
        ForeignKey(
            entity = Vehicle::class,
            parentColumns = ["plateNumber"],
            childColumns = ["vehiclePlateNumber"]
        )
    ]
)
data class Assignment(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val invoiceId: Int,
    val vehiclePlateNumber: String,
    val date: String,
    val availableWeight: Double
)
