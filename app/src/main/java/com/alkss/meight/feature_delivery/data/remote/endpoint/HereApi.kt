package com.alkss.meight.feature_delivery.data.remote.endpoint

import com.alkss.meight.core.HereAPI
import com.alkss.meight.feature_delivery.domain.model.Routes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HereApi {
    @GET("v8/routes?transportMode=car")
    suspend fun getRoute(
        @Query(value = "origin", encoded = true) origin: String,
        @Query(value = "destination", encoded = true) destination: String,
        @Query("return") returnSummary: String = "summary",
        @Query("apiKey") apiKey: String = HereAPI.API_KEY): Routes
}