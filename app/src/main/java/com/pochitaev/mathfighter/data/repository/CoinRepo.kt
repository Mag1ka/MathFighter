package com.pochitaev.mathfighter.data.repository

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.pochitaev.mathfighter.data.dao.CoinDAO
import com.pochitaev.mathfighter.data.dao.ShopDAO
import com.pochitaev.mathfighter.data.db.CoinDB
import com.pochitaev.mathfighter.data.db.ShopDB
import com.pochitaev.mathfighter.data.entity.CoinEntity
import com.pochitaev.mathfighter.data.entity.ShopEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoinRepo(context : Context) {
    var db: CoinDAO = CoinDB.getInstance(context)?.getCoinDAO()!!

    fun coins(coin: CoinEntity) {
        insertAsyncTask(db).execute(coin)
        }
    fun getCoins(): List<CoinEntity> {
        return db.getCoins()
    }
    fun reward(gold: Int) {
        val curCoin = db.currCoins()
        val upCoin = CoinEntity(id = 1, coins = curCoin.coins!! + gold)
            db.coinChange(upCoin)
        }
    fun buy(gold: Int) {
        val curCoin = db.currCoins()
        val upCoin = CoinEntity(id = 1, coins = curCoin.coins!! - gold)
        db.coinChange(upCoin)
    }
private class insertAsyncTask(private val coinDAO: CoinDAO) :
    AsyncTask<CoinEntity, Void, Void>() {

    override fun doInBackground(vararg params: CoinEntity): Void? {
        coinDAO.firstCoin(params[0])
        return null
    }
    }








}