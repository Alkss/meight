package com.alkss.meight.feature_delivery.data.remote.endpoint

import com.alkss.meight.core.HereAPI
import com.alkss.meight.feature_delivery.domain.model.remote.Routes
import retrofit2.http.GET
import retrofit2.http.Query
/**
 * HereApi interface represents the API endpoints for interacting with the Here API.
 */
interface HereApi {
    /**
     * Retrieves the distance between the specified origin and destination using the car transport mode.
     *
     * @param origin The origin location.
     * @param destination The destination location.
     * @param returnSummary The summary information to be returned.
     * @param apiKey The API key for authentication.
     * @return The routes information.
     */
    @GET("v8/routes?transportMode=car")
    suspend fun getDistance(
        @Query(value = "origin", encoded = true) origin: String,
        @Query(value = "destination", encoded = true) destination: String,
        @Query("return") returnSummary: String = "summary",
        @Query("apiKey") apiKey: String = HereAPI.API_KEY
    ): Routes

    /**
     * Retrieves the route between the specified origin, via points, and destination using the car transport mode.
     *
     * @param origin The origin location.
     * @param via The via points to be included in the route.
     * @param destination The destination location.
     * @param returnSummary The summary information to be returned.
     * @param apiKey The API key for authentication.
     * @return The routes information.
     */
    @GET("v8/routes?transportMode=car")
    suspend fun getRoute(
        @Query(value = "origin", encoded = true) origin: String,
        @Query(value = "via", encoded = true) via: List<String>,
        @Query(value = "destination", encoded = true) destination: String,
        @Query("return") returnSummary: String = "summary",
        @Query("apiKey") apiKey: String = HereAPI.API_KEY
    ): Routes
}
