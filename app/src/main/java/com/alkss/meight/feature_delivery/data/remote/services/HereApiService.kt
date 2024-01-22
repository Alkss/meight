package com.alkss.meight.feature_delivery.data.remote.services

import com.alkss.meight.core.NetworkResult
import com.alkss.meight.feature_delivery.data.remote.endpoint.HereApi
import com.alkss.meight.feature_delivery.domain.model.remote.Routes
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Service class for making API calls to the Here API.
 *
 * @param retrofit The Retrofit instance used for making network requests.
 */
class HereApiService @Inject constructor(
    retrofit: Retrofit
) {
    private val api: HereApi = retrofit.create(HereApi::class.java)

    /**
     * Retrieves the distance between two locations.
     *
     * @param origin The origin location.
     * @param destination The destination location.
     * @return A [NetworkResult] object containing the distance information.
     */
    suspend fun getDistance(origin: String, destination: String): NetworkResult<Routes> {
        try {
            val route = api.getDistance(origin, destination)
            return NetworkResult.Success(route)
        } catch (e: Exception) {
            return NetworkResult.Error(e)
        }
    }

    /**
     * Retrieves the route between multiple locations.
     *
     * @param origin The origin location.
     * @param via The list of intermediate locations.
     * @param destination The destination location.
     * @return A [NetworkResult] object containing the route information.
     */
    suspend fun getRoute(origin: String, via: List<String>, destination: String): NetworkResult<Routes> {
        try{
            val route = api.getRoute(origin, via, destination)
            return NetworkResult.Success(route)
        }catch (e: Exception){
            return NetworkResult.Error(e)
        }
    }
}
