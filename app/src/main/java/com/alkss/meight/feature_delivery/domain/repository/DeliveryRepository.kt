package com.alkss.meight.feature_delivery.domain.repository

import com.alkss.meight.feature_delivery.domain.model.InvoiceStatus

interface DeliveryRepository {
    fun getInvoices()

    fun updateInvoice(id: Int, status: InvoiceStatus)

    fun getVehicles()

    fun getAssignments()

    //todo verify if this is needed
    fun updateAssignment(id: Int, status: InvoiceStatus)
}
