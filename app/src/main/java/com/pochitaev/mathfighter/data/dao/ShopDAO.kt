package com.pochitaev.mathfighter.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pochitaev.mathfighter.data.entity.ShopEntity

@Dao
interface ShopDAO {
    @Insert
    fun addItem(category: ShopEntity)

    @Update
    fun updateCategory(category: ShopEntity)

    @Query("SELECT * FROM price")
    fun getAllPrices() : List<ShopEntity>
}