package com.pochitaev.mathfighter.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.pochitaev.mathfighter.App
import com.pochitaev.mathfighter.data.entity.ShopEntity
import com.pochitaev.mathfighter.data.repository.ShopRepo
import com.pochitaev.mathfighter.databinding.ActivityCharacterBinding
import com.pochitaev.mathfighter.viewmodel.CharacterVM
import com.romainpiel.shimmer.Shimmer

class Character : AppCompatActivity() {


    private lateinit var binding: ActivityCharacterBinding
    private lateinit var characterViewModel : CharacterVM
    val shimmer = Shimmer()
    val repo: ShopRepo by lazy { ShopRepo(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()
        setPrice()
        shopButtons()
        shimmer.start(binding.textName)
    }

    private fun shopButtons() {
        binding.healthButt.setOnClickListener {
            TODO()
        }
        binding.timeButt.setOnClickListener {
            TODO()
        }
        binding.coinButt.setOnClickListener {
            TODO()
        }
        binding.scoresButt.setOnClickListener {
            TODO()
        }
        binding.reviveButt.setOnClickListener {
            TODO()
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
        binding.healthPrice.text = healthprice[0].price.toString()
        binding.timePrice.text = timeprice[0].price.toString()
        binding.coinPrice.text = coinsprice[0].price.toString()
        binding.scoresPrice.text = scoresprice[0].price.toString()
        binding.revivePrice.text = reviveprice[0].price.toString()
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