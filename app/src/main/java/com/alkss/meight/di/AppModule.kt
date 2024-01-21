package com.alkss.meight.di

import android.app.Application
import androidx.room.Room
import com.alkss.meight.core.HereAPI
import com.alkss.meight.feature_delivery.data.data_source.DeliveryDatabase
import com.alkss.meight.feature_delivery.data.remote.manager.ServerMockManager
import com.alkss.meight.feature_delivery.data.repository.DeliveryRepositoryImpl
import com.alkss.meight.feature_delivery.domain.repository.DeliveryRepository
import com.alkss.meight.feature_delivery.domain.use_case.invoice.GetInvoicesById
import com.alkss.meight.feature_delivery.domain.use_case.invoice.InvoiceUseCases
import com.alkss.meight.feature_delivery.domain.use_case.invoice.GetInvoicesByVehicle
import com.alkss.meight.feature_delivery.domain.use_case.invoice.GetInvoicesRequest
import com.alkss.meight.feature_delivery.domain.use_case.invoice.InsertInvoiceList
import com.alkss.meight.feature_delivery.domain.use_case.invoice.UpdateInvoice
import com.alkss.meight.feature_delivery.domain.use_case.invoice.UpdateInvoiceRequest
import com.alkss.meight.feature_delivery.domain.use_case.vehicle.GetVehicles
import com.alkss.meight.feature_delivery.domain.use_case.vehicle.VehicleUseCases
import com.alkss.meight.feature_delivery.domain.use_case.vehicle.InsertVehicles
import com.alkss.meight.feature_delivery.domain.use_case.vehicle.RefreshVehicles
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDeliveryRepository(db: DeliveryDatabase): DeliveryRepository {
        return DeliveryRepositoryImpl(
            vehicleDao = db.vehicleDao,
            invoiceDao = db.invoiceDao,
            assignmentDao = db.assignmentDao
        )
    }

    @Provides
    @Singleton
    fun provideDeliveryDatabase(app: Application): DeliveryDatabase {
        return Room.databaseBuilder(
            app,
            DeliveryDatabase::class.java,
            DeliveryDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(HereAPI.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().disableHtmlEscaping().setLenient().create()
                )
            )
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideDeliveryUseCase(
        mockManager: ServerMockManager,
        repository: DeliveryRepository
    ): InvoiceUseCases {
        return InvoiceUseCases(
            getInvoicesRequest = GetInvoicesRequest(mockManager),
            updateInvoice = UpdateInvoice(repository),
            getInvoicesByVehicle = GetInvoicesByVehicle(repository),
            insertInvoiceList = InsertInvoiceList(repository),
            getInvoicesById = GetInvoicesById(repository),
            updateInvoiceRequest = UpdateInvoiceRequest(mockManager)
        )
    }

    @Provides
    @Singleton
    fun provideHomeUseCase(
        repository: DeliveryRepository,
        mockManager: ServerMockManager
    ): VehicleUseCases {
        return VehicleUseCases(
            getVehicles = GetVehicles(repository),
            refreshTrucks = RefreshVehicles(mockManager),
            insertVehicles = InsertVehicles(repository)
        )
    }

    @Provides
    @Singleton
    fun provideServerMockManager() = ServerMockManager()
}
