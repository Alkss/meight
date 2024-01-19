package com.alkss.meight.feature_delivery.data.remote.manager

import com.alkss.meight.feature_delivery.data.remote.services.HereApiService
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.toImmutableList
import javax.inject.Inject

class HereApiManager @Inject constructor(
    private val hereApiService: HereApiService
) {
    /**
     *  - String -> destination
     *  - Int -> distance
     */
    private val path: MutableList<Map<String, Int>> = mutableListOf()
    suspend fun calculateDistance(
        origin: Pair<Double, Double>,
        destinationList: List<Map<String, Pair<Double, Double>>>
    ): List<Map<String, Int>> {
        val originString = "${origin.first},${origin.second}"

        destinationList.forEach { singleDestination ->
            val destinationString = "${singleDestination.values.first().first},${singleDestination.values.first().second}"
            val response = hereApiService.getRoute(originString, destinationString)

            val distance = response.routes[0].sections[0].summary.length
            path.add(mapOf(singleDestination.keys.toString() to distance))
        }


        return path.toImmutableList().sortedBy { it.values.first() }
    }
}