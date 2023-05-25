package com.pochitaev.mathfighter.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.pochitaev.mathfighter.data.repository.ShopRepo
import com.pochitaev.mathfighter.databinding.ActivityShopBinding

class Shop : BaseActivity() {
    val repo: ShopRepo by lazy {ShopRepo(this)}

    private lateinit var binding: ActivityShopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.sback.setOnClickListener {
            val intent = Intent(this@Shop, MainActivity::class.java)
            startActivity(intent) }
    }


}