package com.pochitaev.mathfighter.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.pochitaev.mathfighter.databinding.ActivityMainBinding
import com.pochitaev.mathfighter.utils.showCustomToast
import com.romainpiel.shimmer.Shimmer
import pl.droidsonroids.gif.GifDrawable


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    val shimmer = Shimmer()
    private lateinit var anim : GifDrawable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shimmer.start(binding.textName)
        anim = binding.logoAnim.drawable as GifDrawable



        navigate()

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
    //Exit
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
//        Toast.makeText(this, "Нажмите еще раз для выхода", Toast.LENGTH_SHORT).show()
        showCustomToast(this,"Нажмите еще раз для выхода")
        exitHandler.postDelayed(exitRunnable, 2000)
    }
    override fun onDestroy() {
        exitHandler.removeCallbacks(exitRunnable)
        super.onDestroy()
    }

}