package com.alkss.meight.feature_delivery.data.data_source

import androidx.room.Database
import com.alkss.meight.feature_delivery.domain.model.Invoice
import com.alkss.meight.feature_delivery.domain.model.Vehicle

@Database(entities = [Invoice::class, Vehicle::class], version = 1)
abstract class DeliveryDatabase {
    abstract val invoiceDao: InvoiceDao
    abstract val vehicleDao: VehicleDao

    companion object {
        const val DATABASE_NAME = "delivery_db"
    }
}