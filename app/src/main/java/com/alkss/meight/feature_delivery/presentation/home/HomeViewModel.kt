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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val vehicleUseCases: VehicleUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

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