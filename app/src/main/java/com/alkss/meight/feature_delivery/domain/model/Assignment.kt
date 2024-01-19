package com.alkss.meight.feature_delivery.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Invoice::class,
            parentColumns = ["id"],
            childColumns = ["orderId"]
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
    val date: String
)
