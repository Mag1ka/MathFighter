package com.pochitaev.mathfighter.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pochitaev.mathfighter.data.dao.CoinDAO
import com.pochitaev.mathfighter.data.dao.ShopDAO
import com.pochitaev.mathfighter.data.entity.CoinEntity
import com.pochitaev.mathfighter.data.entity.ShopEntity

@Database(entities = [CoinEntity::class], version = 1, exportSchema = false)
    abstract class CoinDB : RoomDatabase() {

    abstract fun getCoinDAO(): CoinDAO

    companion object {
        private var INSTANCE: CoinDB? = null
        fun getInstance(context: Context): CoinDB? {
            if (INSTANCE == null) {
                synchronized(CoinDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CoinDB::class.java, "coins.db"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE

        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}