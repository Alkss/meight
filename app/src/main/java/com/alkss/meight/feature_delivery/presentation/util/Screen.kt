package com.alkss.meight.feature_delivery.presentation.util

sealed class Screen(val route: String) {
    data object HomeScreen: Screen("home_screen")
    data object DeliveryScreen: Screen("delivery_screen")
    data object InvoiceDetailsScreen: Screen("invoice_details_screen")
}
