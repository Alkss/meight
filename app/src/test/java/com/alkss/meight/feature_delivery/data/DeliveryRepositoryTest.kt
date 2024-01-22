package com.alkss.meight.feature_delivery.data

import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.model.local.InvoiceStatus
import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository
import com.alkss.meight.feature_delivery.domain.use_case.invoice.GetInvoicesById
import com.alkss.meight.feature_delivery.domain.use_case.invoice.GetInvoicesByVehicle
import com.alkss.meight.feature_delivery.domain.use_case.invoice.InsertInvoiceList
import com.alkss.meight.feature_delivery.domain.use_case.invoice.UpdateInvoice
import com.alkss.meight.feature_delivery.domain.use_case.vehicle.GetVehicles
import com.alkss.meight.feature_delivery.domain.use_case.vehicle.InsertVehicles
import com.alkss.meight.feature_delivery.domain.use_case.vehicle.RefreshVehicles
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeliveryRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()

    private var repository: DeliveryRepository = mockk<DeliveryRepository>(relaxed = true)
    private lateinit var getInvoicesById: GetInvoicesById
    private lateinit var getInvoicesByVehicle: GetInvoicesByVehicle
    private lateinit var insertInvoiceList: InsertInvoiceList
    private lateinit var updateInvoice: UpdateInvoice

    private lateinit var getVehicles: GetVehicles
    private lateinit var insertVehicles: InsertVehicles

    private val invoice =
        Invoice(
            id = 4357,
            weight = 18.19,
            destinationLat = 20.21,
            destinationLong = 22.23,
            observations = "unum",
            vehiclePlateNumber = "dolorem",
            status = InvoiceStatus.IN_PROGRESS
        )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getInvoicesById = GetInvoicesById(repository)
        getInvoicesByVehicle = GetInvoicesByVehicle(repository)
        insertInvoiceList = InsertInvoiceList(repository)
        updateInvoice = UpdateInvoice(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get invoice by id`() = runBlocking {
        coEvery { repository.getInvoiceById(any()) } returns invoice
        val response = getInvoicesById.invoke(1)
        assertEquals(response, invoice)
    }

    @Test
    fun `get invoices by vehicle`() = runBlocking {
        coEvery { repository.getInvoiceListByPlateNumber("10") } returns flowOf(
            listOf(
                invoice,
                invoice.copy(id = 2)
            )
        )
        val response = getInvoicesByVehicle.invoke("10")?.first()
        assertEquals(
            response, listOf(
                invoice,
                invoice.copy(id = 2)
            )
        )
    }

    @Test
    fun `insert invoice list`() = runBlocking {
        coEvery { repository.insertAllInvoices(any()) } returns Unit
        val response =
            insertInvoiceList.invoke(listOf(invoice, invoice.copy(id = 2), invoice.copy(id = 3)))
        assertEquals(response, Unit)
    }

    @Test
    fun `update invoice`() = runBlocking {
        coEvery { repository.updateInvoice(any()) } returns Unit
        val response = updateInvoice.invoke(invoice)
        assertEquals(response, Unit)
    }

    @Test
    fun `get vehicles`() = runBlocking {
        coEvery { repository.getVehicles() } returns flowOf(
            listOf(
                Vehicle(plateNumber = "pellentesque", maxWeightCapacity = 26.27),
                Vehicle(plateNumber = "pe22llentesque", maxWeightCapacity = 26.27),
            )
        )
        val response = repository.getVehicles().first()
        assertEquals(
            response, listOf(
                Vehicle(plateNumber = "pellentesque", maxWeightCapacity = 26.27),
                Vehicle(plateNumber = "pe22llentesque", maxWeightCapacity = 26.27),
            )
        )
    }

    @Test
    fun `insert vehicles`() = runBlocking {
        coEvery { repository.insertAllVehicles(any()) } returns Unit
        val response = repository.insertAllVehicles(
            listOf(
                Vehicle(plateNumber = "pellentesque", maxWeightCapacity = 26.27),
                Vehicle(plateNumber = "pe22llentesque", maxWeightCapacity = 26.27),
            )
        )
        assertEquals(response, Unit)
    }
}
