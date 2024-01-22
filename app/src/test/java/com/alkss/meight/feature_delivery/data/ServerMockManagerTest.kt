package com.alkss.meight.feature_delivery.data

import com.alkss.meight.core.HereAPI
import com.alkss.meight.feature_delivery.data.remote.manager.ServerMockManager
import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.model.local.InvoiceStatus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ServerMockManagerTest {

    private val serverMockManager = ServerMockManager()

    @Test
    fun testGetAllInvoices() = runBlocking {
        val invoices = serverMockManager.getAllInvoices().first()
        assertEquals(8, invoices.size)
        assertEquals(7973, invoices[0].id)
        assertEquals(2.3, invoices[0].weight, 0.0)
    }

    @Test
    fun testGetAllVehicles() = runBlocking {
        val vehicles = serverMockManager.getAllVehicles().first()
        assertEquals(3, vehicles.size)
        assertEquals("12345", vehicles[0].plateNumber)
        assertEquals(10.0, vehicles[0].maxWeightCapacity, 0.0)
    }

    @Test
    fun testUpdateInvoiceStatus() {
        val invoice = Invoice(
            id = 7973,
            weight = 2.3,
            destinationLat = HereAPI.STOP_A_LAT,
            destinationLong = HereAPI.STOP_A_LONG,
            observations = "Yellow House 1",
            vehiclePlateNumber = "12345",
            status = InvoiceStatus.IN_PROGRESS
        )
        val result = serverMockManager.updateInvoiceStatus(invoice)
        assertTrue(result)
    }
}
