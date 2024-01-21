package com.alkss.meight.feature_delivery.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {
    @Query("SELECT * FROM invoice")
    fun getAll(): Flow<List<Invoice>>

    @Query("SELECT * FROM invoice WHERE id = :id")
    fun getInvoiceById(id: Int): Invoice?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(invoices: List<Invoice>)

    @Query("SELECT * FROM invoice WHERE vehiclePlateNumber= :plateNumber")
    fun getInvoicesByVehicle(plateNumber: String): Flow<List<Invoice>>?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateInvoice(invoice: Invoice)
}
