package com.alkss.meight.feature_delivery.data.remote.services

import com.alkss.meight.feature_delivery.data.remote.endpoint.HereApi
import com.alkss.meight.feature_delivery.domain.model.Route
import com.alkss.meight.feature_delivery.domain.model.Routes
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class HereApiService @Inject constructor(
    private val retrofit: Retrofit
) {
    private val api: HereApi = retrofit.create(HereApi::class.java)

    suspend fun getRoute(origin: String, destination: String): Routes {
        val route = api.getRoute(origin, destination)
        return route
    }
}
