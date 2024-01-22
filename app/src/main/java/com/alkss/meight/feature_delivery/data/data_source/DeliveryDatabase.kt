package com.alkss.meight.feature_delivery.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alkss.meight.feature_delivery.domain.model.local.Assignment
import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.model.local.Vehicle

/**
 * Represents the DeliveryDatabase, which is a RoomDatabase subclass.
 * This database contains tables for Invoice, Vehicle, and Assignment entities.
 * The current version of the database is 6.
 *
 * @property invoiceDao The DAO (Data Access Object) for Invoice entity.
 * @property vehicleDao The DAO (Data Access Object) for Vehicle entity.
 * @property assignmentDao The DAO (Data Access Object) for Assignment entity.
 */
@Database(entities = [Invoice::class, Vehicle::class, Assignment::class], version = 6)
abstract class DeliveryDatabase: RoomDatabase() {
    abstract val invoiceDao: InvoiceDao
    abstract val vehicleDao: VehicleDao
    abstract val assignmentDao: AssignmentDao

    companion object {
        const val DATABASE_NAME = "delivery_db"
    }
}