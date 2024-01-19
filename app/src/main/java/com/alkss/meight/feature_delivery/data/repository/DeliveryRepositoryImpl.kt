package com.alkss.meight.feature_delivery.data.repository

import com.alkss.meight.feature_delivery.data.data_source.AssignmentDao
import com.alkss.meight.feature_delivery.data.data_source.InvoiceDao
import com.alkss.meight.feature_delivery.data.data_source.VehicleDao
import com.alkss.meight.feature_delivery.domain.model.InvoiceStatus
import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository

class DeliveryRepositoryImpl(
    private val vehicleDao: VehicleDao,
    private val invoiceDao: InvoiceDao,
    private val assignmentDao: AssignmentDao

): DeliveryRepository {
    override fun getInvoices() {
        invoiceDao.getAll()
    }

    override fun updateInvoice(id : Int, status: InvoiceStatus) {
        invoiceDao.insertAll()
    }

    override fun getVehicles() {
        vehicleDao.getAll()
    }

    override fun getAssignments() {
        assignmentDao.getAll()
    }

    override fun updateAssignment(id: Int, status: InvoiceStatus) {
        assignmentDao.insertAll()
    }
}
