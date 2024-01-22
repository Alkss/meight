package com.alkss.meight.feature_delivery.domain.use_case.invoice

import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Use case class that retrieves a list of invoices for a given vehicle.
 *
 * @param repository The repository used to fetch the invoices.
 */
class GetInvoicesByVehicle(
    private val repository: DeliveryRepository
) {
    /**
     * Retrieves a flow of invoices for the specified vehicle.
     *
     * @param vehicleId The ID of the vehicle.
     * @return A flow of invoices sorted by order.
     */
    operator fun invoke(vehicleId: String): Flow<List<Invoice>>? {
        return repository.getInvoiceListByPlateNumber(vehicleId)?.map { invoiceList ->
            invoiceList.sortedBy { it.order }
        }
    }
}
