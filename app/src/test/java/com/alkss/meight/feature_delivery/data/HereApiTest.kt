package com.alkss.meight.feature_delivery.data

import com.alkss.meight.core.HereAPI
import com.alkss.meight.core.NetworkResult
import com.alkss.meight.feature_delivery.data.remote.manager.HereApiManager
import com.alkss.meight.feature_delivery.data.remote.services.HereApiService
import com.alkss.meight.feature_delivery.domain.model.local.Invoice
import com.alkss.meight.feature_delivery.domain.model.local.InvoiceStatus
import com.google.gson.GsonBuilder
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotSame
import junit.framework.AssertionFailedError
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
    fun testGetRoute_returns_at_order(): Unit = runBlocking {
        val response = manager.calculateDistance(
            origin = Pair(52.5308, 13.3847),
            invoiceList = mutableListOf(
                Invoice(
                    id = 1,
                    weight = 6.7,
                    destinationLat = 52.5308,
                    destinationLong = 13.3947,
                    observations = "inani",
                    vehiclePlateNumber = "quaerendum",
                    status = InvoiceStatus.IN_PROGRESS

                ),
                Invoice(
                    id = 2,
                    weight = 6.7,
                    destinationLat = 52.5308,
                    destinationLong = 13.4047,
                    observations = "inani",
                    vehiclePlateNumber = "quaerendum",
                    status = InvoiceStatus.IN_PROGRESS

                ),
                Invoice(
                    id = 3,
                    weight = 6.7,
                    destinationLat = 52.5308,
                    destinationLong = 12.4147,
                    observations = "inani",
                    vehiclePlateNumber = "quaerendum",
                    status = InvoiceStatus.IN_PROGRESS

                ),
                Invoice(
                    id = 4,
                    weight = 6.7,
                    destinationLat = 52.5308,
                    destinationLong = 13.4247,
                    observations = "inani",
                    vehiclePlateNumber = "quaerendum",
                    status = InvoiceStatus.IN_PROGRESS

                ),
            )
        )
        when (response) {
            is NetworkResult.Error -> AssertionFailedError(response.exception.message)
            is NetworkResult.Success -> {
                assertEquals(response.data[0].id, 1)
                assertNotSame(response.data[1].id, 3)
            }
        }
    }
}
