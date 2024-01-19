package com.alkss.meight.feature_delivery.data.data_source

import androidx.room.Insert
import androidx.room.Query
import com.alkss.meight.feature_delivery.domain.model.Vehicle

interface VehicleDao {
    @Query("SELECT * FROM vehicle")
    fun getAll(): List<Vehicle>

    @Insert
    fun insertAll(vararg vehicles: Vehicle)
}
