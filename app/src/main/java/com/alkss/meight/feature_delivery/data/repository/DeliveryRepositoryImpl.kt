package com.alkss.meight.feature_delivery.data.repository

import com.alkss.meight.feature_delivery.data.data_source.AssignmentDao
import com.alkss.meight.feature_delivery.data.data_source.InvoiceDao
import com.alkss.meight.feature_delivery.data.data_source.VehicleDao
import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository
import kotlinx.coroutines.flow.Flow

class DeliveryRepositoryImpl(
    private val vehicleDao: VehicleDao,
    private val invoiceDao: InvoiceDao,
    private val assignmentDao: AssignmentDao

): DeliveryRepository {
    override fun getInvoices(): Flow<List<Invoice>> {
        return invoiceDao.getAll()
    }

    override suspend fun getInvoiceById(id: Int): Invoice? {
        return invoiceDao.getInvoiceById(id)
    }

    override fun getInvoiceListByPlateNumber(plateNumber: String): Flow<List<Invoice>>? {
        return invoiceDao.getInvoicesByVehicle(plateNumber)
    }

    override suspend fun insertAllInvoices(invoices: List<Invoice>) {
        invoiceDao.insertAll(invoices)
    }

    override suspend fun updateInvoice(invoice: Invoice) {
        invoiceDao.updateInvoice(invoice)
    }

    override fun getVehicles(): Flow<List<Vehicle>> {
        return vehicleDao.getAll()
    }

    override suspend fun getVehicleById(id: Int): Vehicle? {
        return vehicleDao.getVehicleById(id)
    }

    override suspend fun insertAllVehicles(vehicles: List<Vehicle>) {
        vehicleDao.insertAll(vehicles)
    }

    override suspend fun getAssignments() {
        assignmentDao.getAll()
    }

    override fun updateAssignment(id: Int) {
        assignmentDao.insertAll()
    }
}
