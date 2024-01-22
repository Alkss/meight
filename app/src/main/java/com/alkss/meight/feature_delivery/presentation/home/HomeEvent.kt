package com.alkss.meight.feature_delivery.presentation.home

/**
 * Represents events related to the home screen in the delivery feature.
 */
sealed class HomeEvent {
    /**
     * Represents a request to refresh the vehicles on the home screen.
     */
    data object RefreshVehiclesRequest : HomeEvent()
}