package com.alkss.meight.feature_delivery.presentation.home

import com.alkss.meight.feature_delivery.domain.model.local.Vehicle

data class HomeUiState(
    val vehicles: List<Vehicle> = emptyList(),
)
