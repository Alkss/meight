package com.alkss.meight.feature_delivery.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkss.meight.feature_delivery.domain.use_case.vehicle.VehicleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for the Home screen.
 *
 * This class is responsible for handling the business logic and state management of the Home screen.
 * It uses the [VehicleUseCases] to interact with the data layer and provides the necessary data to the UI.
 *
 * @property vehicleUseCases The use cases for vehicle-related operations.
 * @property state The state flow representing the UI state of the Home screen.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val vehicleUseCases: VehicleUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    /**
     * Handles the events triggered by the UI.
     *
     * @param event The event to be handled.
     */
    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.RefreshVehiclesRequest -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val vehicleList = vehicleUseCases.refreshTrucks().first()
                    _state.update {
                        it.copy(
                            vehicles = vehicleList
                        )
                    }
                    vehicleUseCases.insertVehicles(vehicleList)
                }
            }
        }
    }
}