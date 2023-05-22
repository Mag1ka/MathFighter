package com.pochitaev.mathfighter.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.pochitaev.mathfighter.databinding.ActivityMainBinding
import com.romainpiel.shimmer.Shimmer


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    val shimmer = Shimmer()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shimmer.start(binding.textName)
        //functions
        hideSystemBars()
        navigate()

    }
    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        supportActionBar?.hide()

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
            val intent = Intent(this@MainActivity, Achievements::class.java)
            startActivity(intent)}
        bLeaderboards.setOnClickListener {
            val intent = Intent(this@MainActivity, Leaderboards::class.java)
            startActivity(intent)}
    }

    override fun onBackPressed() {

    }

}