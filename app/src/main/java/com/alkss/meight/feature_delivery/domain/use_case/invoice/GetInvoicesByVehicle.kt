package com.alkss.meight.feature_delivery.domain.use_case.invoice

import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetInvoicesByVehicle(
    private val repository: DeliveryRepository
) {
    operator fun invoke(vehicleId: String): Flow<List<Invoice>>? {
        return repository.getInvoiceListByPlateNumber(vehicleId)?.map { invoiceList ->
            invoiceList.sortedBy { it.order }
        }
    }
}
