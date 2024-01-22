package com.alkss.meight.feature_delivery.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alkss.meight.feature_delivery.domain.model.local.Assignment

/**
 * Data Access Object (DAO) interface for the Assignment entity.
 */
@Dao
interface AssignmentDao {
    /**
     * Retrieves all assignments from the database.
     *
     * @return a list of all assignments.
     */
    @Query("SELECT * FROM assignment")
    fun getAll(): List<Assignment>

    /**
     * Inserts multiple assignments into the database.
     *
     * @param assignments the assignments to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg assignments: Assignment)
}