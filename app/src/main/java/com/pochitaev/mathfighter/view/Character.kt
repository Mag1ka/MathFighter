package com.pochitaev.mathfighter.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.pochitaev.mathfighter.App
import com.pochitaev.mathfighter.R
import com.pochitaev.mathfighter.data.entity.ShopEntity
import com.pochitaev.mathfighter.data.repository.CoinRepo
import com.pochitaev.mathfighter.data.repository.ShopRepo
import com.pochitaev.mathfighter.databinding.ActivityCharacterBinding
import com.pochitaev.mathfighter.viewmodel.CharacterVM
import com.romainpiel.shimmer.Shimmer

class Character : AppCompatActivity() {


    private lateinit var binding: ActivityCharacterBinding
    private lateinit var characterViewModel : CharacterVM
    val shimmer = Shimmer()
    val repo: ShopRepo by lazy { ShopRepo(this) }
    val coinRepo: CoinRepo by lazy { CoinRepo(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()
        setPrice()
        setCoins()
        shimmer.start(binding.textName)
//HealthBuy
        binding.healthButt.setOnClickListener {
            if (coinRepo.getCoins()[0].coins!! > binding.healthPrice.text.toString().toInt()){
            when(binding.healthPrice.text.toString()){
                "200"   -> repo.updatePrice(ShopEntity(id = 1,name = "Health" , price = "200" , isSolded = true, value = 150))
                "500"   -> repo.updatePrice(ShopEntity(id = 2,name = "Health" , price = "500" , isSolded = true, value = 200))
                "1000"  -> repo.updatePrice(ShopEntity(id = 3,name = "Health" , price = "1000" , isSolded = true, value = 250))
                "2000"  -> repo.updatePrice(ShopEntity(id = 4,name = "Health" , price = "2000" , isSolded = true, value = 300))
                "5000"  -> repo.updatePrice(ShopEntity(id = 5,name = "Health" , price = "5000" , isSolded = true, value = 350))
            }
            setPrice()
        }
        else Toast.makeText(this, "You don't have enough coins!", Toast.LENGTH_LONG).show()
        }
//ScoresBuy
        binding.scoresButt.setOnClickListener {
            if (coinRepo.getCoins()[0].coins!! > binding.scoresPrice.text.toString().toInt()){
            when(binding.scoresPrice.text.toString()){
                "100"  -> repo.updatePrice((ShopEntity(id = 6, name = "Score" , price = "100" , isSolded = true, value = 110)))
                "300"  -> repo.updatePrice((ShopEntity(id = 7, name = "Score" , price = "300" , isSolded = true, value = 120)))
                "500"  -> repo.updatePrice((ShopEntity(id = 8, name = "Score" , price = "500" , isSolded = true, value = 130)))
                "700"  -> repo.updatePrice((ShopEntity(id = 9, name = "Score" , price = "700" , isSolded = true, value = 140)))
                "900"  -> repo.updatePrice((ShopEntity(id = 10, name = "Score" , price = "900" ,isSolded = true, value = 150)))
            }
            setPrice()
        }
            else Toast.makeText(this, "You don't have enough coins!", Toast.LENGTH_LONG).show()
        }
//TimeBuy
        binding.timeButt.setOnClickListener {
            if (coinRepo.getCoins()[0].coins!! > binding.timePrice.text.toString().toInt()){
            when(binding.timePrice.text.toString()){
                "100"  -> repo.updatePrice((ShopEntity(id = 11, name = "Time" , price = "100" , isSolded = true, value = 5000 )))
                "300"  -> repo.updatePrice((ShopEntity(id = 12, name = "Time" , price = "300" , isSolded = true, value = 10000)))
                "500"  -> repo.updatePrice((ShopEntity(id = 13, name = "Time" , price = "500" , isSolded = true, value = 15000)))
                "700"  -> repo.updatePrice((ShopEntity(id = 14, name = "Time" , price = "700" , isSolded = true, value = 20000)))
                "900"  -> repo.updatePrice((ShopEntity(id = 15, name = "Time" , price = "900" , isSolded = true, value = 25000)))
            }
            setPrice()
        }
            else Toast.makeText(this, "You don't have enough coins!", Toast.LENGTH_LONG).show()
        }
//CoinsBuy
        binding.coinButt.setOnClickListener {
            if (coinRepo.getCoins()[0].coins!! > binding.coinPrice.text.toString().toInt()){
            when(binding.coinPrice.text.toString()){
                "100"  -> repo.updatePrice((ShopEntity(id = 16, name = "Gold" , price = "100" , isSolded = true, value = 110)))
                "300"  -> repo.updatePrice((ShopEntity(id = 17, name = "Gold" , price = "300" , isSolded = true, value = 120)))
                "500"  -> repo.updatePrice((ShopEntity(id = 18, name = "Gold" , price = "500" , isSolded = true, value = 130)))
                "700"  -> repo.updatePrice((ShopEntity(id = 19, name = "Gold" , price = "700" , isSolded = true, value = 140)))
                "900"  -> repo.updatePrice((ShopEntity(id = 20, name = "Gold" , price = "900" , isSolded = true, value = 150)))
            }
            setPrice()
        }
        else Toast.makeText(this, "You don't have enough coins!", Toast.LENGTH_LONG).show()
    }
//ReviveBuy
        binding.reviveButt.setOnClickListener {
            if (coinRepo.getCoins()[0].coins!! > binding.revivePrice.text.toString().toInt()){
            when(binding.revivePrice.text.toString()){
                "10000"  -> repo.updatePrice((ShopEntity(id = 21, name = "Revive" , price = "10000" , isSolded = true, value = 1)))
            }
            setPrice()
        }
            else Toast.makeText(this, "You don't have enough coins!", Toast.LENGTH_LONG).show()
        }

        binding.back.setOnClickListener {
            val intent = Intent(this@Character, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    private fun setPrice() {
        val healthprice = mutableListOf<ShopEntity>()
        val timeprice = mutableListOf<ShopEntity>()
        val coinsprice = mutableListOf<ShopEntity>()
        val scoresprice = mutableListOf<ShopEntity>()
        val reviveprice = mutableListOf<ShopEntity>()
        val allPrices = repo.getCategories()
        allPrices.forEach {
            when (it.name) {
                "Health" -> if(!it.isSolded) {healthprice.add(it)}
                "Time" -> if(!it.isSolded) {timeprice.add(it)}
                "Gold" -> if(!it.isSolded) {coinsprice.add(it)}
                "Score" -> if(!it.isSolded){scoresprice.add(it)}
                "Revive" -> if(!it.isSolded) {reviveprice.add(it)}
            }
        }
        //health
        if(healthprice.isNotEmpty()){
            binding.healthPrice.text = healthprice[0].price.toString()}
        else {binding.healthPrice.text = getString(R.string.shop_max)
              binding.healthButt.isClickable = false}
        //time
        if(timeprice.isNotEmpty()){
            binding.timePrice.text = timeprice[0].price.toString()}
        else {binding.timePrice.text = getString(R.string.shop_max)
              binding.timeButt.isClickable = false}
        //coin
        if(coinsprice.isNotEmpty()){
            binding.coinPrice.text = coinsprice[0].price.toString()}
        else {binding.coinPrice.text = getString(R.string.shop_max)
              binding.coinButt.isClickable = false}
        //scores
        if(scoresprice.isNotEmpty()){
              binding.scoresPrice.text = scoresprice[0].price.toString()}
        else {binding.scoresPrice.text = getString(R.string.shop_max)
              binding.scoresButt.isClickable = false}
        //revive
        if(reviveprice.isNotEmpty()){
              binding.revivePrice.text = reviveprice[0].price.toString()}
        else {binding.revivePrice.text = getString(R.string.shop_max)
              binding.reviveButt.isClickable = false}
    }
    private fun setCoins(){
    binding.coins.text = coinRepo.getCoins()[0].coins.toString()
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