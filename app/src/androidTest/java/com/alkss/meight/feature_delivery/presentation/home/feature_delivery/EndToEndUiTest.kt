package com.alkss.meight.feature_delivery.presentation.home.feature_delivery

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alkss.meight.core.TestTags
import com.alkss.meight.core.TestTags.DELIVERED_BUTTON
import com.alkss.meight.core.TestTags.DELIVERY_SCREEN
import com.alkss.meight.core.TestTags.INVOICE_DETAILS_SCREEN
import com.alkss.meight.core.TestTags.NOT_DELIVERED_BUTTON
import com.alkss.meight.core.TestTags.NO_TRUCKS_AVAILABLE
import com.alkss.meight.core.TestTags.REFRESH_INVOICES
import com.alkss.meight.core.TestTags.REFRESH_VEHICLES
import com.alkss.meight.di.AppModule
import com.alkss.meight.feature_delivery.presentation.MainActivity
import com.alkss.meight.feature_delivery.presentation.delivery.DeliveryScreen
import com.alkss.meight.feature_delivery.presentation.delivery.DeliveryViewModel
import com.alkss.meight.feature_delivery.presentation.home.HomeScreen
import com.alkss.meight.feature_delivery.presentation.invoice_detail.InvoiceDetailViewModel
import com.alkss.meight.feature_delivery.presentation.invoice_detail.InvoiceDetailsScreen
import com.alkss.meight.feature_delivery.presentation.util.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class EndToEndUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            val navController = rememberNavController()
            MaterialTheme {
                Surface(Modifier.fillMaxSize()) {
                    NavHost(navController = navController, startDestination = Screen.HomeScreen.route ){
                        composable(Screen.HomeScreen.route) {
                            HomeScreen(navController = navController)
                        }
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

    @Test
    fun noVehiclesAreVisibleAtStart() {
        composeTestRule.onNodeWithTag(NO_TRUCKS_AVAILABLE).assertExists()
    }

    @Test
    fun vehiclesAreVisibleAfterRefresh() {
        composeTestRule.onNodeWithTag(NO_TRUCKS_AVAILABLE).assertExists()
        composeTestRule.onNodeWithTag(REFRESH_VEHICLES).performClick()
        composeTestRule.onNodeWithTag("vehicle_item_1").assertExists()
    }


    @Test
    fun noInvoicesAreDisplayed(){
        composeTestRule.onNodeWithTag(NO_TRUCKS_AVAILABLE).assertExists()
        composeTestRule.onNodeWithTag(REFRESH_VEHICLES).performClick()
        composeTestRule.onNodeWithTag("vehicle_item_1").assertExists()
        composeTestRule.onNodeWithTag("vehicle_item_1").performClick()

        composeTestRule.onNodeWithTag("delivery_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag(DELIVERY_SCREEN).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.NO_INVOICES_AVAILABLE).assertExists()
    }

    @Test
    fun invoicesAreDisplayedAfterRefresh(){
        composeTestRule.onNodeWithTag(NO_TRUCKS_AVAILABLE).assertExists()
        composeTestRule.onNodeWithTag(REFRESH_VEHICLES).performClick()
        composeTestRule.onNodeWithTag("vehicle_item_1").assertExists()
        composeTestRule.onNodeWithTag("vehicle_item_1").performClick()

        composeTestRule.onNodeWithTag("delivery_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag(DELIVERY_SCREEN).assertIsDisplayed()
        composeTestRule.onNodeWithTag(REFRESH_INVOICES).performClick()
        composeTestRule.onNodeWithTag("index_1").assertExists()
    }

    @Test
    fun invoiceDetailsIsDisplayed(){
        composeTestRule.onNodeWithTag(NO_TRUCKS_AVAILABLE).assertExists()
        composeTestRule.onNodeWithTag(REFRESH_VEHICLES).performClick()
        composeTestRule.onNodeWithTag("vehicle_item_1").assertExists()
        composeTestRule.onNodeWithTag("vehicle_item_1").performClick()

        composeTestRule.onNodeWithTag("delivery_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag(REFRESH_INVOICES).performClick()
        composeTestRule.onNodeWithTag("index_1").assertExists()
        composeTestRule.onNodeWithTag("index_1").performClick()

        composeTestRule.onNodeWithTag(INVOICE_DETAILS_SCREEN).assertIsDisplayed()
    }

    @Test
    fun invoiceDetailsClickDelivered(){
        composeTestRule.onNodeWithTag(NO_TRUCKS_AVAILABLE).assertExists()
        composeTestRule.onNodeWithTag(REFRESH_VEHICLES).performClick()
        composeTestRule.onNodeWithTag("vehicle_item_1").assertExists()
        composeTestRule.onNodeWithTag("vehicle_item_1").performClick()

        composeTestRule.onNodeWithTag("delivery_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag(REFRESH_INVOICES).performClick()
        composeTestRule.onNodeWithTag("index_1").assertExists()
        composeTestRule.onNodeWithTag("index_1").performClick()

        composeTestRule.onNodeWithTag(INVOICE_DETAILS_SCREEN).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DELIVERED_BUTTON).performClick()
        composeTestRule.onNodeWithTag(DELIVERED_BUTTON).assertIsNotEnabled()
    }

    @Test
    fun invoiceDetailsClickNotDelivered(){
        composeTestRule.onNodeWithTag(NO_TRUCKS_AVAILABLE).assertExists()
        composeTestRule.onNodeWithTag(REFRESH_VEHICLES).performClick()
        composeTestRule.onNodeWithTag("vehicle_item_1").assertExists()
        composeTestRule.onNodeWithTag("vehicle_item_1").performClick()

        composeTestRule.onNodeWithTag("delivery_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag(REFRESH_INVOICES).performClick()
        composeTestRule.onNodeWithTag("index_1").assertExists()
        composeTestRule.onNodeWithTag("index_1").performClick()

        composeTestRule.onNodeWithTag(INVOICE_DETAILS_SCREEN).assertIsDisplayed()
        composeTestRule.onNodeWithTag(NOT_DELIVERED_BUTTON).performClick()
        composeTestRule.onNodeWithTag(NOT_DELIVERED_BUTTON).assertIsNotEnabled()
    }
}
