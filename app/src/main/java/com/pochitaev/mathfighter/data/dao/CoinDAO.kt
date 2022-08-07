package com.pochitaev.mathfighter.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pochitaev.mathfighter.data.entity.CoinEntity
import com.pochitaev.mathfighter.data.entity.ShopEntity

@Dao
interface CoinDAO {
    @Insert
    fun firstCoin(coin: CoinEntity)

    @Update
    fun coinChange(coin: CoinEntity)

    @Query("SELECT * FROM coins")
    fun getCoins() : List<CoinEntity>

    @Query("SELECT * FROM coins WHERE id = 1")
    fun currCoins() : CoinEntity



}