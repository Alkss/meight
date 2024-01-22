package com.alkss.meight.feature_delivery.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alkss.meight.feature_delivery.domain.model.local.Vehicle
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for accessing the Vehicle table in the database.
 */
@Dao
interface VehicleDao {
    /**
     * Retrieves all vehicles from the Vehicle table.
     * @return a Flow of List<Vehicle> representing all the vehicles in the table.
     */
    @Query("SELECT * FROM vehicle")
    fun getAll(): Flow<List<Vehicle>>

    /**
     * Retrieves a vehicle from the Vehicle table based on the provided id.
     * @param id the id of the vehicle to retrieve.
     * @return the Vehicle object with the provided id, or null if no vehicle is found.
     */
    @Query("SELECT * FROM vehicle WHERE plateNumber = :id")
    fun getVehicleById(id: Int): Vehicle?

    /**
     * Inserts a list of vehicles into the Vehicle table.
     * If a vehicle with the same primary key already exists, it will be replaced.
     * @param vehicles the list of vehicles to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vehicles: List<Vehicle>)

    /**
     * Updates a vehicle in the Vehicle table.
     * If a vehicle with the same primary key already exists, it will be replaced.
     * @param vehicle the vehicle to update.
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateVehicle(vehicle: Vehicle)
}
