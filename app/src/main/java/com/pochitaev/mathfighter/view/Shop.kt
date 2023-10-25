package com.pochitaev.mathfighter.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import com.android.billingclient.api.*
import com.pochitaev.mathfighter.data.repository.CoinRepo
import com.pochitaev.mathfighter.databinding.ActivityShopBinding
import com.android.billingclient.api.Purchase
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.pochitaev.mathfighter.R
import com.pochitaev.mathfighter.utils.showCustomToast

class Shop : BaseActivity() {

    val repo: CoinRepo by lazy {CoinRepo(this)}

    private lateinit var binding: ActivityShopBinding
    private lateinit var billingClient: BillingClient
    private val AD_REMOVE = "ad_remove"
    val coinRepo: CoinRepo by lazy { CoinRepo(this) }
    private var rewardedAd: RewardedAd? = null
    private var isLoaded = false
    private final var TAG = "Shop"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadRewardedAd()
        val sp = getSharedPreferences(AD_REMOVE, Context.MODE_PRIVATE)
        val hasBought = sp.getBoolean("hasBought", false)
        if (hasBought){binding.noAdB.visibility= GONE
        binding.noAdBalready.visibility= VISIBLE}
        binding.sback.setOnClickListener {
            val intent = Intent(this@Shop, MainActivity::class.java)
            startActivity(intent) }



        setupBillingClient()
        binding.freeCoinButt.setOnClickListener {
            if (isLoaded){showRewardedVideo()
            }else{loadRewardedAd()
                showCustomToast(this, getString(R.string.ad_load))
            }
        }
        binding.coins1000b.setOnClickListener {
            initiatePurchase("1000_coins")
        }

        binding.coins2000b.setOnClickListener {
            initiatePurchase("2000_coins")
        }

        binding.coins4000b.setOnClickListener {
            initiatePurchase("4000_coins")
        }

        binding.noAdB.setOnClickListener { if(!hasBought){
            initiatePurchase("remove_ad")
        }else showCustomToast(this, getString(R.string.ad_already_bought))

        }
    }

    private fun setupBillingClient() {
        billingClient = BillingClient.newBuilder(this)
            .setListener(purchaseUpdateListener)
            .enablePendingPurchases()
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
//                    querySkuDetails() // Запрашиваем SKU-детали после успешной инициализации BillingClient
                    querySkuDetails("1000_coins")
                    querySkuDetails("2000_coins")
                    querySkuDetails("4000_coins")
                    querySkuDetails("remove_ad")
                } else {
                    // Обработка ошибки подключения к сервису билинга
                }
            }

            override fun onBillingServiceDisconnected() {
                // Handle disconnection
            }
        })
    }

    private val purchaseUpdateListener = PurchasesUpdatedListener { _, purchases ->
        purchases?.let {
            handlePurchases(it)
            purchases.clear()
        }
    }


    private fun handlePurchases(purchases: List<Purchase>) {
        val sp = getSharedPreferences(AD_REMOVE, Context.MODE_PRIVATE)
        for (purchase in purchases) {
            if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                val skuDetails = purchase.skus.firstOrNull()
                skuDetails?.let {
                    when (it) {
                        "1000_coins" -> handlePurchase1000Coins(purchase)
                        "2000_coins" -> handlePurchase2000Coins(purchase)
                        "4000_coins" -> handlePurchase4000Coins(purchase)
                        "remove_ad" -> handlePurchaseRemoveAd(purchase, sp)
                    }
                    // Потребляем покупку
                    consumePurchase(purchase)

                }
            }
        }
    }
    private fun handlePurchase1000Coins(purchase: Purchase) {
        repo.reward(1000)
        consumePurchase(purchase)
    }

    private fun handlePurchase2000Coins(purchase: Purchase) {
        repo.reward(2000)
        consumePurchase(purchase)
    }

    private fun handlePurchase4000Coins(purchase: Purchase) {
        repo.reward(4000)
        consumePurchase(purchase)
    }

    private fun handlePurchaseRemoveAd(purchase: Purchase, sp: SharedPreferences) {
        val editor = sp.edit()
        editor.putBoolean("hasBought", true)
        editor.apply()
        consumePurchase(purchase)
    }


    private fun consumePurchase(purchase: Purchase) {
        val consumeParams = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        billingClient.consumeAsync(consumeParams) { _, _ ->
            // Обработка завершения потребления покупки (если необходимо)
        }
    }
    private fun initiatePurchase(productId: String) {
        querySkuDetails(productId) // Запрашиваем SKU-детали для товара
    }

    private fun querySkuDetails(productId: String) {
        val params = SkuDetailsParams.newBuilder()
            .setSkusList(listOf(productId))
            .setType(BillingClient.SkuType.INAPP)
            .build()

        billingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            if (skuDetailsList != null) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList.isNotEmpty()) {
                    val skuDetails = skuDetailsList[0] // Получаем SKU-детали для конкретного товара
                    runOnUiThread {
                        when (skuDetails.sku) {
                            "1000_coins" -> {
                                binding.coins1000p.text = "${skuDetails.price}"
                                binding.coins1000b.setOnClickListener {
                                    initiateBillingFlow(skuDetails)
                                }
                            }
                            "2000_coins" -> {
                                binding.coins2000p.text = "${skuDetails.price}"
                                binding.coins2000b.setOnClickListener {
                                    initiateBillingFlow(skuDetails)
                                }
                            }
                            "4000_coins" -> {
                                binding.coins4000p.text = "${skuDetails.price}"
                                binding.coins4000b.setOnClickListener {
                                    initiateBillingFlow(skuDetails)
                                }
                            }
                            "remove_ad" -> {
                                binding.noAdP.text = "${skuDetails.price}"
                                binding.noAdB.setOnClickListener { initiateBillingFlow(skuDetails) }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initiateBillingFlow(skuDetails: SkuDetails) {
        val flowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetails)
            .build()

        billingClient.launchBillingFlow(this, flowParams)
    }
    private fun loadRewardedAd(){
        var adRequest = AdRequest.Builder().build()
        RewardedAd.load(this, getString(R.string.ad_ingame), adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.toString())
                rewardedAd = null
                loadRewardedAd()
                isLoaded = false
            }

            override fun onAdLoaded(ad: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                rewardedAd = ad
                isLoaded = true
            }
        })
    }
    private fun showRewardedVideo(){
        if (rewardedAd != null) {
            rewardedAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        rewardedAd = null
                        loadRewardedAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        rewardedAd = null
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                        // Called when ad is dismissed.
                    }
                }

            rewardedAd?.show(
                this,
                OnUserEarnedRewardListener {
                    repo.reward(100)
                    Log.d("TAG", "User earned the reward.")
                }
            )
        }
    }
}