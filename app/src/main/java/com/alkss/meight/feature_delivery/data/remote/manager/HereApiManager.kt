package com.alkss.meight.feature_delivery.data.remote.manager

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.alkss.meight.core.NetworkResult
import com.alkss.meight.feature_delivery.data.remote.services.HereApiService
import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class HereApiManager @Inject constructor(
    private val hereApiService: HereApiService
) {
    suspend fun calculateDistance(
        origin: Pair<Double, Double>,
        invoiceList: MutableList<Invoice>
    ): NetworkResult<List<Invoice>> {
//        val destination = calculateFarthest(origin, invoiceList)
        val destination =
            invoiceList.random() //takes a random entry from the list because the API is not a fan of the amount of requests
        val filteredList = invoiceList.filter { it.id != destination.id }
       when (val orderedList = hereApiService.getRoute(
            "${origin.first},${origin.second}",
            filteredList.map { "${it.destinationLat},${it.destinationLong}" },
            "${destination.destinationLat},${destination.destinationLong}"
        )) {
            is NetworkResult.Error -> return NetworkResult.Error(orderedList.exception)
            is NetworkResult.Success -> {
                val mappedList = orderedList.data.routes[0].sections.mapIndexedNotNull { index, section ->
                    val responseLat = String.format("%.2f", section.arrival.place.location.lat)
                    val responseLong = String.format("%.2f", section.arrival.place.location.lng)

                    val invoice =
                        invoiceList.find {
                            val mineLat = String.format("%.2f", it.destinationLat)
                            val mineLong = String.format("%.2f", it.destinationLong)

                            mineLat == responseLat && mineLong == responseLong
                        }
                    invoice?.let {
                        invoiceList.remove(it)
                        it.copy(order = index)
                    }
                }
                return NetworkResult.Success(mappedList)
            }
        }
    }

    private suspend fun calculateFarthest(
        origin: Pair<Double, Double>,
        invoiceList: List<Invoice>
    ): NetworkResult<Invoice> {
        var maxDistance = 0
        var furthestInvoice: Invoice? = null

        invoiceList.forEach { invoice ->
            val distance = try {
                Log.d(
                    "HereApiManager",
                    "calculateFarthest: ${invoice.destinationLat},${invoice.destinationLong}"
                )
                getDistance(
                    origin,
                    Pair(invoice.destinationLat, invoice.destinationLong)
                )
            } catch (e: HttpException) {
                // Log the exception
                Log.d("HereApiManager", "calculateFarthest: ${e.message}")
                return NetworkResult.Error(e)
            }
            when (distance) {
                is NetworkResult.Error -> return NetworkResult.Error(distance.exception)
                is NetworkResult.Success -> {
                    if (distance.data > maxDistance) {
                        maxDistance = distance.data
                        furthestInvoice = invoice
                    }
                }
            }

        }

        return NetworkResult.Success(furthestInvoice!!)
    }

    private suspend fun getDistance(
        pointA: Pair<Double, Double>,
        pointB: Pair<Double, Double>
    ): NetworkResult<Int> {
        val originString = "${pointA.first},${pointA.second}"
        val destinationString = "${pointB.first},${pointB.second}"
        when (val response = hereApiService.getDistance(originString, destinationString)) {
            is NetworkResult.Error -> {
                Log.d("HereApiManager", "getDistance: ${response.exception.message}")
                return NetworkResult.Error(response.exception)
            }

            is NetworkResult.Success -> {
                Log.d(
                    "HereApiManager",
                    "getDistance: ${response.data.routes[0].sections[0].summary.length}"
                )
                return NetworkResult.Success(response.data.routes[0].sections[0].summary.length)
            }
        }
    }

}
