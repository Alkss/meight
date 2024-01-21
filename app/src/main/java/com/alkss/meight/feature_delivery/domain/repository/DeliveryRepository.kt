package com.alkss.meight.feature_delivery.domain.repository

import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
import kotlinx.coroutines.flow.Flow

interface DeliveryRepository {

    //Invoices
    fun getInvoices(): Flow<List<Invoice>>

    suspend fun getInvoiceById(id: Int): Invoice?

    fun getInvoiceListByPlateNumber(plateNumber: String): Flow<List<Invoice>>?

    suspend fun updateInvoice(invoice: Invoice)

    suspend fun insertAllInvoices(invoices: List<Invoice>)


    //Vehicle
    fun getVehicles() : Flow<List<Vehicle>>

    suspend fun getVehicleById(id: Int): Vehicle?

    suspend fun insertAllVehicles(vehicles: List<Vehicle>)


    //Assignments
    suspend fun getAssignments()

    fun updateAssignment(id: Int)

}
