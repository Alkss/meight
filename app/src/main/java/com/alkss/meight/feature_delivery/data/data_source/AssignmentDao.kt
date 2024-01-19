package com.alkss.meight.feature_delivery.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alkss.meight.feature_delivery.domain.model.Assignment

@Dao
interface AssignmentDao {
    @Query("SELECT * FROM assignment")
    fun getAll(): List<Assignment>

    @Insert
    fun insertAll(vararg assignments: Assignment)
}