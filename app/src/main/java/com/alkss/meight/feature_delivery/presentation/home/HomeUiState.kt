package com.alkss.meight.feature_delivery.presentation.home

import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
/**
 * Represents the UI state of the home screen in the delivery feature.
 *
 * @property vehicles The list of vehicles displayed on the home screen.
 */
data class HomeUiState(
    val vehicles: List<Vehicle> = emptyList(),
)
