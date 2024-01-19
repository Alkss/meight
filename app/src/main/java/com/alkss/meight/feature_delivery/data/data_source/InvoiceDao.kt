package com.alkss.meight.feature_delivery.data.data_source

import androidx.room.Insert
import androidx.room.Query
import com.alkss.meight.feature_delivery.domain.model.Invoice

interface InvoiceDao {
    @Query("SELECT * FROM invoice")
    fun getAll(): List<Invoice>

    @Insert
    fun insertAll(vararg invoices: Invoice)
}
