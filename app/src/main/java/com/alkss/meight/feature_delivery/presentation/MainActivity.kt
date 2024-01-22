package com.alkss.meight.feature_delivery.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alkss.meight.feature_delivery.presentation.delivery.DeliveryScreen
import com.alkss.meight.feature_delivery.presentation.delivery.DeliveryViewModel
import com.alkss.meight.feature_delivery.presentation.home.HomeScreen
import com.alkss.meight.feature_delivery.presentation.invoice_detail.InvoiceDetailViewModel
import com.alkss.meight.feature_delivery.presentation.invoice_detail.InvoiceDetailsScreen
import com.alkss.meight.feature_delivery.presentation.util.Screen
import com.alkss.meight.ui.theme.MeightTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity is the entry point of the delivery feature in the Meight app.
 * It extends ComponentActivity and is annotated with @AndroidEntryPoint for Hilt dependency injection.
 * The activity sets up the navigation graph using Jetpack Compose and handles the creation of different screens.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is being created.
     * It initializes the UI and sets up the navigation graph.
     * The content of the activity is defined using Jetpack Compose.
     * The navigation graph consists of multiple composable screens.
     * The start destination is set to the HomeScreen.
     * The activity also enables edge-to-edge display.
     */
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeightTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        modifier = Modifier.padding(16.dp),
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    ) {
                        // HomeScreen composable
                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(
                                navController = navController
                            )
                        }
                        
                        // DeliveryScreen composable
                        composable(
                            route = Screen.DeliveryScreen.route + "?vehiclePlate={vehiclePlate}",
                            arguments = listOf(navArgument(
                                name = "vehiclePlate"
                            ) {
                                type = NavType.StringType
                                defaultValue = ""
                            })
                        ) {
                            val deliveryViewModel: DeliveryViewModel = hiltViewModel()
                            val vehiclePlate = it.arguments?.getString("vehiclePlate") ?: ""
                            deliveryViewModel.updateVehicleId(vehiclePlate)

                            DeliveryScreen(
                                viewModel = deliveryViewModel, navController = navController
                            )
                        }
                        
                        // InvoiceDetailsScreen composable
                        composable(
                            route = Screen.InvoiceDetailsScreen.route
                                    + "?invoiceId={invoiceId}"
                                    + "&nextInvoiceId={nextInvoiceId}"
                                    + "&vehiclePlate={vehiclePlate}",
                            arguments = listOf(
                                navArgument(
                                    name = "invoiceId"
                                ) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(
                                    name = "nextInvoiceId"
                                ) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(
                                    name = "vehiclePlate"
                                ) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )
                        ) {
                            val invoiceDetailViewModel: InvoiceDetailViewModel = hiltViewModel()
                            val invoiceId = it.arguments?.getString("invoiceId") ?: ""
                            val nextInvoiceId = it.arguments?.getString("nextInvoiceId") ?: ""
                            val vehiclePlate = it.arguments?.getString("vehiclePlate") ?: ""

                            invoiceDetailViewModel.updateVehicleId(vehiclePlate)
                            invoiceDetailViewModel.updateInvoiceId(invoiceId)
                            invoiceDetailViewModel.updateNextInvoiceId(nextInvoiceId)

                            InvoiceDetailsScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}