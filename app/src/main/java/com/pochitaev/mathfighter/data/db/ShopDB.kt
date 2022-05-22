package com.pochitaev.mathfighter.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pochitaev.mathfighter.data.dao.ShopDAO
import com.pochitaev.mathfighter.data.entity.ShopEntity

@Database(entities = [ShopEntity::class], version = 1, exportSchema = false)
    abstract class ShopDB : RoomDatabase() {

    abstract fun getShopDAO(): ShopDAO

    companion object {
        private var INSTANCE: ShopDB? = null
        fun getInstance(context: Context): ShopDB? {
            if (INSTANCE == null) {
                synchronized(ShopDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ShopDB::class.java, "games.db"
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