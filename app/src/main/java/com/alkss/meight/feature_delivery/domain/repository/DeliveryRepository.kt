package com.alkss.meight.feature_delivery.domain.repository

import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
import kotlinx.coroutines.flow.Flow

/**
 * This interface represents a repository for managing delivery-related data.
 */
interface DeliveryRepository {

    /**
     * Retrieves a flow of all invoices.
     *
     * @return A flow emitting a list of invoices.
     */
    fun getInvoices(): Flow<List<Invoice>>

    /**
     * Retrieves an invoice by its ID.
     *
     * @param id The ID of the invoice to retrieve.
     * @return The invoice with the specified ID, or null if not found.
     */
    suspend fun getInvoiceById(id: Int): Invoice?

    /**
     * Retrieves a flow of invoices filtered by plate number.
     *
     * @param plateNumber The plate number to filter by.
     * @return A flow emitting a list of invoices matching the plate number.
     */
    fun getInvoiceListByPlateNumber(plateNumber: String): Flow<List<Invoice>>?

    /**
     * Updates an existing invoice.
     *
     * @param invoice The invoice to update.
     */
    suspend fun updateInvoice(invoice: Invoice)

    /**
     * Inserts a list of invoices.
     *
     * @param invoices The list of invoices to insert.
     */
    suspend fun insertAllInvoices(invoices: List<Invoice>)


    /**
     * Retrieves a flow of all vehicles.
     *
     * @return A flow emitting a list of vehicles.
     */
    fun getVehicles() : Flow<List<Vehicle>>

    /**
     * Retrieves a vehicle by its ID.
     *
     * @param id The ID of the vehicle to retrieve.
     * @return The vehicle with the specified ID, or null if not found.
     */
    suspend fun getVehicleById(id: Int): Vehicle?

    /**
     * Inserts a list of vehicles.
     *
     * @param vehicles The list of vehicles to insert.
     */
    suspend fun insertAllVehicles(vehicles: List<Vehicle>)


    /**
     * Retrieves the assignments.
     *
     * @return The assignments.
     */
    suspend fun getAssignments()

    /**
     * Updates an assignment by its ID.
     *
     * @param id The ID of the assignment to update.
     */
    fun updateAssignment(id: Int)

}
