package com.pochitaev.mathfighter.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.gms.games.PlayGames
import com.google.android.gms.tasks.Task
import com.pochitaev.mathfighter.App
import com.pochitaev.mathfighter.R
import com.pochitaev.mathfighter.databinding.ActivityMainBinding
import com.pochitaev.mathfighter.utils.showCustomToast
import com.romainpiel.shimmer.Shimmer
import pl.droidsonroids.gif.GifDrawable


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    val shimmer = Shimmer()
    private lateinit var anim : GifDrawable
    var isAuth = false
    private val RC_SIGN_IN = 9001
    private val RC_LEADERBOARD_UI = 9004;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shimmer.start(binding.textName)
        anim = binding.logoAnim.drawable as GifDrawable
        authCheck()
        navigate()

    }

    private fun authCheck() {
        val gamesSignInClient = PlayGames.getGamesSignInClient(this)
        gamesSignInClient
            .requestServerSideAccess("216836792617-dilh6jvv6ak5203bei6q5kg2bffouehc.apps.googleusercontent.com",  /* forceRefreshToken= */false)
            .addOnCompleteListener { task: Task<String?> ->
                if (task.isSuccessful) {
                    val serverAuthToken = task.result
                    // Send authentication code to the backend game server to be
                    // exchanged for an access token and used to verify the player
                    // via the Play Games Services REST APIs.
                } else {
                    val exception = task.exception
                    Log.e("AuthCheck", "Failed to retrieve authentication code", exception)
                    // Failed to retrieve authentication code.
                }
            }
    }


    private fun navigate(){
        val bStart = binding.startButton
        val bCharacter = binding.characterButton
        val bShop = binding.shopButton
        val bSetting = binding.settingButton
        val bAchievements = binding.achievementButton
        val bLeaderboards = binding.leaderboardsButton



        bStart.setOnClickListener {
            val intent = Intent(this@MainActivity, Game::class.java)
            startActivity(intent)
        }
        bCharacter.setOnClickListener {
            val intent = Intent(this@MainActivity, Character::class.java)
            startActivity(intent)}
        bShop.setOnClickListener {
            val intent = Intent(this@MainActivity, Shop::class.java)
            startActivity(intent)}
        bSetting.setOnClickListener {
            val intent = Intent(this@MainActivity, Options::class.java)
            startActivity(intent)}
        bAchievements.setOnClickListener {
//            val intent = Intent(this@MainActivity, Achievements::class.java)
//            startActivity(intent)
        showCustomToast(this, getString(R.string.sign_todo))}
        bLeaderboards.setOnClickListener {
                val leaderboardsClient = PlayGames.getLeaderboardsClient(this)
                leaderboardsClient.getLeaderboardIntent(getString(R.string.leaderboardsID))
                    .addOnSuccessListener { intent ->
                        startActivityForResult(intent, RC_LEADERBOARD_UI)
            }
        }
    }
    private var doubleBackToExitPressedOnce = false
    private val exitHandler = Handler()
    private val exitRunnable = Runnable { doubleBackToExitPressedOnce = false }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            exitHandler.removeCallbacks(exitRunnable)
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }

        this.doubleBackToExitPressedOnce = true
        showCustomToast(this,"Нажмите еще раз для выхода")
        exitHandler.postDelayed(exitRunnable, 2000)
    }
    override fun onDestroy() {
        exitHandler.removeCallbacks(exitRunnable)
        super.onDestroy()
        App.mediaPlayer.stop()
        App.mediaPlayer.release()
    }
    override fun onPause() {
        super.onPause()
        if (App.mediaPlayer.isPlaying) {
            App.mediaPlayer.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!App.mediaPlayer.isPlaying) {
            App.mediaPlayer.start()
        }
    }

}