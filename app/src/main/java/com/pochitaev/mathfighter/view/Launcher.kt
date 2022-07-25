package com.pochitaev.mathfighter.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.pochitaev.mathfighter.data.entity.CoinEntity
import com.pochitaev.mathfighter.data.entity.ShopEntity
import com.pochitaev.mathfighter.data.repository.CoinRepo
import com.pochitaev.mathfighter.data.repository.ShopRepo
import com.pochitaev.mathfighter.databinding.ActivityLauncherBinding

class Launcher : AppCompatActivity() {
    private val MY_SETTINGS = "my_settings"
    val repo: ShopRepo by lazy {ShopRepo(this)}
    val coinRepo: CoinRepo by lazy {CoinRepo(this)}



    private lateinit var binding: ActivityLauncherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this@Launcher, MainActivity::class.java)
        startActivity(intent)
        hideSystemBars()
        start()

    }
    private fun start(){
        val sp = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE)
        val hasVisited = sp.getBoolean("hasVisited", false)

        if (!hasVisited){
            val e = sp.edit()
            e.putBoolean("hasVisited", true)
            e.apply()
//            FirstCoin
        coinRepo.coins(CoinEntity(coins = 0))
//            Health
        repo.insertPrice(ShopEntity(name = "Health" , price = "200" , isSolded =  false, value = 150))
        repo.insertPrice(ShopEntity(name = "Health" , price = "500" , isSolded =  false, value = 200))
        repo.insertPrice(ShopEntity(name = "Health" , price = "1000" , isSolded = false, value = 250))
        repo.insertPrice(ShopEntity(name = "Health" , price = "2000" , isSolded = false, value = 300))
        repo.insertPrice(ShopEntity(name = "Health" , price = "5000" , isSolded = false, value = 350))

//            Score
        repo.insertPrice(ShopEntity(name = "Score" , price = "100" , isSolded = false, value = 110))
        repo.insertPrice(ShopEntity(name = "Score" , price = "300" , isSolded = false, value = 120))
        repo.insertPrice(ShopEntity(name = "Score" , price = "500" , isSolded = false, value = 130))
        repo.insertPrice(ShopEntity(name = "Score" , price = "700" , isSolded = false, value = 140))
        repo.insertPrice(ShopEntity(name = "Score" , price = "900" , isSolded = false, value = 150))

//            time
        repo.insertPrice(ShopEntity(name = "Time" , price = "100" , isSolded = false, value = 5000 ))
        repo.insertPrice(ShopEntity(name = "Time" , price = "300" , isSolded = false, value = 10000))
        repo.insertPrice(ShopEntity(name = "Time" , price = "500" , isSolded = false, value = 15000))
        repo.insertPrice(ShopEntity(name = "Time" , price = "700" , isSolded = false, value = 20000))
        repo.insertPrice(ShopEntity(name = "Time" , price = "900" , isSolded = false, value = 25000))
//            gold
        repo.insertPrice(ShopEntity(name = "Gold" , price = "100" , isSolded = false, value = 110))
        repo.insertPrice(ShopEntity(name = "Gold" , price = "300" , isSolded = false, value = 120))
        repo.insertPrice(ShopEntity(name = "Gold" , price = "500" , isSolded = false, value = 130))
        repo.insertPrice(ShopEntity(name = "Gold" , price = "700" , isSolded = false, value = 140))
        repo.insertPrice(ShopEntity(name = "Gold" , price = "900" , isSolded = false, value = 150))
//            revive
        repo.insertPrice(ShopEntity(name = "Revive" , price = "10000" , isSolded = false, value = 1))



        }
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
}