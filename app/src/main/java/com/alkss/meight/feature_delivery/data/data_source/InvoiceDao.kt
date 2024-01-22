package com.alkss.meight.feature_delivery.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for the Invoice entity.
 */
@Dao
interface InvoiceDao {
    /**
     * Retrieves all invoices from the database.
     * @return A Flow emitting a list of invoices.
     */
    @Query("SELECT * FROM invoice")
    fun getAll(): Flow<List<Invoice>>

    /**
     * Retrieves an invoice by its ID.
     * @param id The ID of the invoice to retrieve.
     * @return The invoice with the specified ID, or null if not found.
     */
    @Query("SELECT * FROM invoice WHERE id = :id")
    fun getInvoiceById(id: Int): Invoice?

    /**
     * Inserts a list of invoices into the database.
     * If an invoice with the same ID already exists, it will be replaced.
     * @param invoices The list of invoices to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(invoices: List<Invoice>)

    /**
     * Retrieves all invoices associated with a specific vehicle.
     * @param plateNumber The plate number of the vehicle.
     * @return A Flow emitting a list of invoices associated with the vehicle.
     */
    @Query("SELECT * FROM invoice WHERE vehiclePlateNumber= :plateNumber")
    fun getInvoicesByVehicle(plateNumber: String): Flow<List<Invoice>>?

    /**
     * Updates an existing invoice in the database.
     * If the invoice does not exist, it will be inserted.
     * @param invoice The invoice to update.
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateInvoice(invoice: Invoice)
}
