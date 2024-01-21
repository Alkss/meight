package com.alkss.meight.feature_delivery.presentation.home

sealed class HomeEvent {
    data object RefreshVehiclesRequest : HomeEvent()
}