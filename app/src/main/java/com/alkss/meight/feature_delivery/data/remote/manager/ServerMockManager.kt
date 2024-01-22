package com.alkss.meight.feature_delivery.data.remote.manager

import com.alkss.meight.core.HereAPI
import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.model.local.InvoiceStatus
import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ServerMockManager {
    fun getAllInvoices(): Flow<List<Invoice>> {
        return flowOf(
            listOf(
                Invoice(
                    id = 7973,
                    weight = 2.3,
                    destinationLat = HereAPI.STOP_A_LAT,
                    destinationLong = HereAPI.STOP_A_LONG,
                    observations = "Yellow House 1",
                    vehiclePlateNumber = "12345",
                    status = InvoiceStatus.IN_PROGRESS
                ),
                Invoice(
                    id = 79173,
                    weight = 2.3,
                    destinationLat = HereAPI.STOP_A_LAT,
                    destinationLong = HereAPI.STOP_A_LONG,
                    observations = "Yellow House 2",
                    vehiclePlateNumber = "54321",
                    status = InvoiceStatus.IN_PROGRESS
                ),
                Invoice(
                    id = 7974452,
                    weight = 2.3,
                    destinationLat = HereAPI.STOP_B_LAT,
                    destinationLong = HereAPI.STOP_B_LONG,
                    observations = "Red House 1",
                    vehiclePlateNumber = "12345",
                    status = InvoiceStatus.IN_PROGRESS
                ),
                Invoice(
                    id = 7974,
                    weight = 2.3,
                    destinationLat = HereAPI.STOP_B_LAT,
                    destinationLong = HereAPI.STOP_B_LONG,
                    observations = "Red House 1",
                    vehiclePlateNumber = "12456",
                    status = InvoiceStatus.IN_PROGRESS
                ),
                Invoice(
                    id = 71974,
                    weight = 2.3,
                    destinationLat = HereAPI.STOP_B_LAT,
                    destinationLong = HereAPI.STOP_B_LONG,
                    observations = "Red House 2",
                    vehiclePlateNumber = "54321",
                    status = InvoiceStatus.IN_PROGRESS
                ),
                Invoice(
                    id = 7975,
                    weight = 2.3,
                    destinationLat = HereAPI.STOP_C_LAT,
                    destinationLong = HereAPI.STOP_C_LONG,
                    observations = "Blue House 1",
                    vehiclePlateNumber = "12345",
                    status = InvoiceStatus.IN_PROGRESS
                ),
                Invoice(
                    id = 71975,
                    weight = 2.3,
                    destinationLat = HereAPI.STOP_C_LAT,
                    destinationLong = HereAPI.STOP_C_LONG,
                    observations = "Blue House 2",
                    vehiclePlateNumber = "54321",
                    status = InvoiceStatus.IN_PROGRESS
                ),
                Invoice(
                    id = 7976,
                    weight = 2.3,
                    destinationLat = HereAPI.STOP_D_LAT,
                    destinationLong = HereAPI.STOP_D_LONG,
                    observations = "Pink House 1",
                    vehiclePlateNumber = "12345",
                    status = InvoiceStatus.IN_PROGRESS
                ),
                Invoice(
                    id = 791776,
                    weight = 2.3,
                    destinationLat = HereAPI.STOP_D_LAT,
                    destinationLong = HereAPI.STOP_D_LONG,
                    observations = "Pink House 2",
                    vehiclePlateNumber = "54321",
                    status = InvoiceStatus.IN_PROGRESS
                ),
            )
        )
    }

    fun getAllVehicles(): Flow<List<Vehicle>> {
        return flowOf(
            listOf(
                Vehicle(
                    plateNumber = "12345",
                    maxWeightCapacity = 10.0,
                ),
                Vehicle(
                    plateNumber = "54321",
                    maxWeightCapacity = 20.0,
                ),
                Vehicle(
                    plateNumber = "12456",
                    maxWeightCapacity = 5.0,
                )
            )
        )
    }

    fun updateInvoiceStatus(invoice: Invoice): Boolean {
        return true
    }
}