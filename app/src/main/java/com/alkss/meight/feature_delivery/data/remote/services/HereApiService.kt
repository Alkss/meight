package com.alkss.meight.feature_delivery.data.remote.services

import com.alkss.meight.feature_delivery.data.remote.endpoint.HereApi
import com.alkss.meight.feature_delivery.domain.model.remote.Routes
import retrofit2.Retrofit
import javax.inject.Inject

class HereApiService @Inject constructor(
    private val retrofit: Retrofit
) {
    private val api: HereApi = retrofit.create(HereApi::class.java)

    suspend fun getDistance(origin: String, destination: String): Routes {
        val route = api.getDistance(origin, destination)
        return route
    }

    suspend fun getRoute(origin: String, via: List<String>, destination: String): Routes {
        val route = api.getRoute(origin, via, destination)
        return route
    }
}
