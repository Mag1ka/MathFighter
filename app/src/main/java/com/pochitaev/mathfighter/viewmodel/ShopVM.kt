package com.pochitaev.mathfighter.viewmodel

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.billingclient.api.*

class ShopVM : ViewModel() {

    private lateinit var billingClient: BillingClient
    private val _skuDetailsList = MutableLiveData<List<SkuDetails>>()
    val skuDetailsList: LiveData<List<SkuDetails>> = _skuDetailsList
    private val _billingConnectionState = MutableLiveData<Boolean>()
    val billingConnectionState: LiveData<Boolean> = _billingConnectionState


    fun initBillingClient(activity: AppCompatActivity) {

        billingClient = BillingClient.newBuilder(activity)
            .setListener(purchaseUpdateListener)
            .enablePendingPurchases()
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                _billingConnectionState.value = billingResult.responseCode == BillingClient.BillingResponseCode.OK
            }

            override fun onBillingServiceDisconnected() {
                _billingConnectionState.value = false
            }
        })
    }

    fun querySkuDetails() {
        val skuList = listOf("1000_coins", "2000_coins", "4000_coins", "remove_ad")

        val params = SkuDetailsParams.newBuilder()
            .setSkusList(skuList)
            .setType(BillingClient.SkuType.INAPP) // Или BillingClient.SkuType.SUBS для подписок
            .build()

        billingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                _skuDetailsList.postValue(skuDetailsList)
            }
        }
    }

    fun purchaseProduct(productId: String, activity: Activity) {
        val skuDetails = _skuDetailsList.value?.find { it.sku == productId }

        skuDetails?.let {
            val flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(it)
                .build()

            billingClient.launchBillingFlow(activity, flowParams)
        }
    }

    private val purchaseUpdateListener = PurchasesUpdatedListener { billingResult, purchases ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            // Обработка обновлений покупок
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (::billingClient.isInitialized) {
            billingClient.endConnection()
        }
    }
}
