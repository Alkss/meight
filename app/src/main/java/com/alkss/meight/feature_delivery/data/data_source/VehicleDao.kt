package com.alkss.meight.feature_delivery.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicle")
    fun getAll(): Flow<List<Vehicle>>

    @Query("SELECT * FROM vehicle WHERE plateNumber = :id")
    fun getVehicleById(id: Int): Vehicle?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vehicles: List<Vehicle>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateVehicle(vehicle: Vehicle)
}
