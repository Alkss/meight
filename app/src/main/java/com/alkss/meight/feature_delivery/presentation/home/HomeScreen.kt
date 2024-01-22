package com.alkss.meight.feature_delivery.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alkss.meight.R
import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
import com.alkss.meight.feature_delivery.presentation.util.Screen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeUiState = homeViewModel.state.collectAsState().value

    Box(modifier = modifier.fillMaxSize()) {
        if (homeUiState.vehicles.isEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "No trucks available"
            )
        }else{
            VehicleSelection(homeUiState, navController)
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.BottomCenter),
            onClick = {
                homeViewModel.onEvent(HomeEvent.RefreshVehiclesRequest)
            }
        ) {
            Text(text = "Refresh Trucks")
        }
    }
}

@Composable
private fun VehicleSelection(
    homeUiState: HomeUiState,
    navController: NavController
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(bottom = 48.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(text = "Available Trucks", style = MaterialTheme.typography.headlineLarge)
        LazyVerticalGrid(
            contentPadding = PaddingValues(8.dp),
            columns = GridCells.Adaptive(minSize = 128.dp)
        ) {
            itemsIndexed(homeUiState.vehicles) { _, vehicle ->
                VehicleBlock(
                    modifier = Modifier.clickable {
                        navController.navigate(
                            Screen.DeliveryScreen.route +
                                    "?vehiclePlate=${vehicle.plateNumber}"
                        )
                    },
                    vehicle = vehicle
                )
            }
        }
    }
}

@Composable
private fun VehicleBlock(modifier: Modifier = Modifier, vehicle: Vehicle) {
    ElevatedCard(
        modifier = modifier
            .padding(8.dp)
            .width(128.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            val painter = painterResource(id = R.drawable.icon_truck)
            Image(
                modifier = Modifier.size(50.dp),
                painter = painter,
                contentDescription = null
            )
            Text(text = "\"${vehicle.plateNumber}\"")
            Text(text = "Capacity: ${vehicle.maxWeightCapacity}kg")
        }
    }
}