package com.alkss.meight.feature_delivery.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alkss.meight.feature_delivery.domain.model.local.Assignment
import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.model.local.Vehicle

@Database(entities = [Invoice::class, Vehicle::class, Assignment::class], version = 6)
abstract class DeliveryDatabase: RoomDatabase() {
    abstract val invoiceDao: InvoiceDao
    abstract val vehicleDao: VehicleDao
    abstract val assignmentDao: AssignmentDao

    companion object {
        const val DATABASE_NAME = "delivery_db"
    }
}