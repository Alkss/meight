package com.alkss.meight

import com.alkss.meight.core.HereAPI
import com.alkss.meight.feature_delivery.data.remote.manager.HereApiManager
import com.alkss.meight.feature_delivery.data.remote.services.HereApiService
import com.google.gson.GsonBuilder
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HereApiTest {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(HereAPI.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().disableHtmlEscaping().setLenient().create()
            )
        )
        .build()

    private val service = HereApiService(retrofit)

    private val manager = HereApiManager(service)

    @Test
    fun `testGetRoute_returns_at_order`() = runBlocking {
        val response = manager.calculateDistance(
            origin = Pair(52.5308, 13.3847),
            destinationList = listOf(
                mapOf("destination" to Pair(52.5308, 13.3947)),
                mapOf("destination 2" to Pair(52.5308, 13.4047)),
                mapOf("destination 3" to Pair(52.5308, 12.4147)),
                mapOf("destination 4" to Pair(52.5308, 13.4247)),

            )
        )
        assertEquals(response[0].keys.toString(), "[[destination]]")
        assertEquals(response[1].keys.toString(), "[[destination 2]]")
        assertEquals(response[2].keys.toString(), "[[destination 4]]")
        assertEquals(response[3].keys.toString(), "[[destination 3]]")
    }
}
