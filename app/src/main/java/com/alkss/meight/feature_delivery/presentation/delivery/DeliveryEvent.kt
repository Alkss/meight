package com.alkss.meight.feature_delivery.presentation.delivery

/**
 * Represents the events related to delivery.
 */
sealed class DeliveryEvent {
    /**
     * Represents the event to get invoices by vehicle.
     * @property plateNumber The plate number of the vehicle.
     */
    data class GetInvoicesByVehicle(val plateNumber: String) : DeliveryEvent()

    /**
     * Represents the event to get invoices request.
     */
    data object GetInvoicesRequest: DeliveryEvent()
}