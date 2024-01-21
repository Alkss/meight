package com.alkss.meight.feature_delivery.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alkss.meight.feature_delivery.domain.model.local.Assignment

@Dao
interface AssignmentDao {
    @Query("SELECT * FROM assignment")
    fun getAll(): List<Assignment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg assignments: Assignment)
}