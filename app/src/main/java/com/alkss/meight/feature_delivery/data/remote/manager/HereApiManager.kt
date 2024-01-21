package com.alkss.meight.feature_delivery.data.remote.manager

import com.alkss.meight.feature_delivery.data.remote.services.HereApiService
import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.toImmutableList
import javax.inject.Inject

class HereApiManager @Inject constructor(
    private val hereApiService: HereApiService
) {
    suspend fun calculateDistance(
        origin: Pair<Double, Double>,
        invoiceList: List<Invoice>
    ): List<Invoice> {
        val unOrderedList: MutableList<Pair<Invoice, Int>> = mutableListOf()
        var currentOrigin = origin
        val remainingInvoices = invoiceList.toMutableList()

        while (remainingInvoices.isNotEmpty()) {
            var minDistance = Int.MAX_VALUE
            var closestInvoice: Invoice? = null

            remainingInvoices.forEach { invoice ->
                val distance = getDistance(
                    currentOrigin,
                    Pair(invoice.destinationLat, invoice.destinationLong)
                )
                if (distance < minDistance) {
                    minDistance = distance
                    closestInvoice = invoice
                }
            }

            if (closestInvoice != null) {
                unOrderedList.add(Pair(closestInvoice!!, minDistance))
                remainingInvoices.remove(closestInvoice)
                currentOrigin =
                    Pair(closestInvoice!!.destinationLat, closestInvoice!!.destinationLong)
            }
        }

        return unOrderedList.sortedBy { it.second }.map { it.first }.toImmutableList()
    }

    private suspend fun getDistance(
        pointA: Pair<Double, Double>,
        pointB: Pair<Double, Double>
    ): Int {
        val originString = "${pointA.first},${pointA.second}"
        val destinationString = "${pointB.first},${pointB.second}"
        val response = hereApiService.getRoute(originString, destinationString)
        return response.routes[0].sections[0].summary.length
    }
}