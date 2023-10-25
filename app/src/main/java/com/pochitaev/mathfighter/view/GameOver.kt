package com.pochitaev.mathfighter.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.games.PlayGames
import com.pochitaev.mathfighter.R
import com.pochitaev.mathfighter.data.repository.CoinRepo
import com.pochitaev.mathfighter.databinding.ActivityGameOverBinding
import com.pochitaev.mathfighter.utils.showCustomToast
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView


class GameOver : BaseActivity() {

    private lateinit var binding: ActivityGameOverBinding
    private lateinit var cd: GifDrawable
    private final var TAG = "GameOver"
    val coinRepo: CoinRepo by lazy { CoinRepo(this) }
    private var rewardedAd: RewardedAd? = null
    private var isLoaded = false
    private val AD_REMOVE = "ad_remove"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sp = getSharedPreferences(AD_REMOVE, Context.MODE_PRIVATE)
        val hasBought = sp.getBoolean("hasBought", false)
        loadRewardedAd()
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        binding.frameLayout.startAnimation(fadeInAnimation)
        gText()
        hAnim()
        coinRepo.reward(intent.getIntExtra("coinsPack", 0))
        if (hasBought){doubleMoney()}
        binding.adDouble.setOnClickListener { if(isLoaded)
        {showRewardedVideo()}
        else {loadRewardedAd()
        showCustomToast(this,getString(R.string.ad_load))}}
        binding.goButt.setOnClickListener {
            PlayGames.getLeaderboardsClient(this)
                .submitScore(getString(R.string.leaderboardsID),
                    intent.getIntExtra("scorePack", 0).toLong());
            val intent = Intent(this@GameOver, MainActivity::class.java)
            startActivity(intent)

        }
    }
    private fun hAnim(){
        cd = findViewById<GifImageView>(R.id.char_death2).drawable as GifDrawable
        val cd2 = findViewById<GifImageView>(R.id.char_death2)
        Handler().postDelayed({
            cd2.visibility = View.VISIBLE
            cd.reset()
        }, 300)
    }
    private fun gText(){
        val coins = intent.getIntExtra("coinsPack", 0)
        val score = intent.getIntExtra("scorePack", 0)
        val ctext = binding.goReward.text.toString()
        binding.goReward.setText(ctext+ " " +coins)
        binding.goScore.setText("You earned "+ score + " score")
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
                    doubleMoney()
                    Log.d("TAG", "User earned the reward.")
                }
            )
        }
    }
    private fun doubleMoney() {
        coinRepo.reward(intent.getIntExtra("coinsPack", 0))
        val coins = (intent.getIntExtra("coinsPack", 0))*2
        val ctext = "You earned reward"
        binding.goReward.setText(ctext+ " " + coins)
        binding.adDouble.visibility = GONE
        binding.adText.visibility = GONE
    }
}