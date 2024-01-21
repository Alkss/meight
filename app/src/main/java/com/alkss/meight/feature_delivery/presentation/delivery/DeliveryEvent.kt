package com.alkss.meight.feature_delivery.presentation.delivery

sealed class DeliveryEvent {
    data class GetInvoicesByVehicle(val plateNumber: String) : DeliveryEvent()
    data object GetInvoicesRequest: DeliveryEvent()
}