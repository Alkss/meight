package com.alkss.meight.feature_delivery.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alkss.meight.feature_delivery.domain.model.Assignment
import com.alkss.meight.feature_delivery.domain.model.Invoice
import com.alkss.meight.feature_delivery.domain.model.Vehicle

@Database(entities = [Invoice::class, Vehicle::class, Assignment::class], version = 1)
abstract class DeliveryDatabase: RoomDatabase() {
    abstract val invoiceDao: InvoiceDao
    abstract val vehicleDao: VehicleDao
    abstract val assignmentDao: AssignmentDao

    companion object {
        const val DATABASE_NAME = "delivery_db"
    }
}