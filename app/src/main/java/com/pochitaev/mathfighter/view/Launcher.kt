package com.pochitaev.mathfighter.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.google.android.gms.games.AuthenticationResult
import com.google.android.gms.games.GamesSignInClient
import com.google.android.gms.games.PlayGames
import com.google.android.gms.tasks.Task
import com.pochitaev.mathfighter.R
import com.pochitaev.mathfighter.data.entity.CoinEntity
import com.pochitaev.mathfighter.data.entity.ShopEntity
import com.pochitaev.mathfighter.data.repository.CoinRepo
import com.pochitaev.mathfighter.data.repository.ShopRepo
import com.pochitaev.mathfighter.databinding.ActivityLauncherBinding
import com.romainpiel.shimmer.Shimmer
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView


class Launcher : BaseActivity() {
    private val MY_SETTINGS = "my_settings"
    val repo: ShopRepo by lazy {ShopRepo(this)}
    val coinRepo: CoinRepo by lazy {CoinRepo(this)}
    val shimmer = Shimmer()
    private lateinit var logoDance: GifDrawable



    private lateinit var binding: ActivityLauncherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        start()
        shimmer.start(binding.logoName)

    }
    private fun start(){
        logoDance = findViewById<GifImageView>(R.id.logo_dance).drawable as GifDrawable
        logoDance.reset()
        logoDance.start()

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
            Handler().postDelayed({
                val intent = Intent(this@Launcher, MainActivity::class.java)
                startActivity(intent)
            }, 3000)


        }
        else Handler().postDelayed({
            val intent = Intent(this@Launcher, MainActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}