package com.pochitaev.mathfighter.data.repository

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.pochitaev.mathfighter.data.dao.ShopDAO
import com.pochitaev.mathfighter.data.db.ShopDB
import com.pochitaev.mathfighter.data.entity.ShopEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopRepo(context : Context) {
    var db: ShopDAO = ShopDB.getInstance(context)?.getShopDAO()!!

    fun getCategories(): List<ShopEntity> {
        return db.getAllPrices()
    }

    fun insertPrice(price: ShopEntity) {
        insertAsyncTask(db).execute(price)
        }


    fun updatePrice(price: ShopEntity) {
            db.updateCategory(price)
        }
private class insertAsyncTask(private val shopDAO: ShopDAO) :
    AsyncTask<ShopEntity, Void, Void>() {

    override fun doInBackground(vararg params: ShopEntity): Void? {
        shopDAO.addItem(params[0])
        return null
    }
    }








}