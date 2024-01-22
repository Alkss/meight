package com.alkss.meight.feature_delivery.data.repository

import com.alkss.meight.feature_delivery.data.data_source.AssignmentDao
import com.alkss.meight.feature_delivery.data.data_source.InvoiceDao
import com.alkss.meight.feature_delivery.data.data_source.VehicleDao
import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of the [DeliveryRepository] interface.
 * This class provides methods to interact with the data sources for deliveries.
 *
 * @property vehicleDao The DAO for accessing vehicle data.
 * @property invoiceDao The DAO for accessing invoice data.
 * @property assignmentDao The DAO for accessing assignment data.
 */
class DeliveryRepositoryImpl(
    private val vehicleDao: VehicleDao,
    private val invoiceDao: InvoiceDao,
    private val assignmentDao: AssignmentDao
): DeliveryRepository {
    /**
     * Retrieves a flow of all invoices.
     *
     * @return A flow emitting a list of [Invoice] objects.
     */
    override fun getInvoices(): Flow<List<Invoice>> {
        return invoiceDao.getAll()
    }

    /**
     * Retrieves an invoice by its ID.
     *
     * @param id The ID of the invoice to retrieve.
     * @return The [Invoice] object with the specified ID, or null if not found.
     */
    override suspend fun getInvoiceById(id: Int): Invoice? {
        return invoiceDao.getInvoiceById(id)
    }

    /**
     * Retrieves a flow of invoices associated with a specific vehicle's plate number.
     *
     * @param plateNumber The plate number of the vehicle.
     * @return A flow emitting a list of [Invoice] objects associated with the specified vehicle.
     */
    override fun getInvoiceListByPlateNumber(plateNumber: String): Flow<List<Invoice>>? {
        return invoiceDao.getInvoicesByVehicle(plateNumber)
    }

    /**
     * Inserts a list of invoices into the data source.
     *
     * @param invoices The list of [Invoice] objects to insert.
     */
    override suspend fun insertAllInvoices(invoices: List<Invoice>) {
        invoiceDao.insertAll(invoices)
    }

    /**
     * Updates an existing invoice in the data source.
     *
     * @param invoice The [Invoice] object to update.
     */
    override suspend fun updateInvoice(invoice: Invoice) {
        invoiceDao.updateInvoice(invoice)
    }

    /**
     * Retrieves a flow of all vehicles.
     *
     * @return A flow emitting a list of [Vehicle] objects.
     */
    override fun getVehicles(): Flow<List<Vehicle>> {
        return vehicleDao.getAll()
    }

    /**
     * Retrieves a vehicle by its ID.
     *
     * @param id The ID of the vehicle to retrieve.
     * @return The [Vehicle] object with the specified ID, or null if not found.
     */
    override suspend fun getVehicleById(id: Int): Vehicle? {
        return vehicleDao.getVehicleById(id)
    }

    /**
     * Inserts a list of vehicles into the data source.
     *
     * @param vehicles The list of [Vehicle] objects to insert.
     */
    override suspend fun insertAllVehicles(vehicles: List<Vehicle>) {
        vehicleDao.insertAll(vehicles)
    }

    /**
     * Retrieves all assignments.
     */
    override suspend fun getAssignments() {
        assignmentDao.getAll()
    }

    /**
     * Updates an assignment with the specified ID.
     *
     * @param id The ID of the assignment to update.
     */
    override fun updateAssignment(id: Int) {
        assignmentDao.insertAll()
    }
}
