package com.pochitaev.mathfighter.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.pochitaev.mathfighter.databinding.ActivityAchievmentsBinding

class Achievements : BaseActivity() {
    private lateinit var binding: ActivityAchievmentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.aback.setOnClickListener {
            val intent = Intent(this@Achievements, MainActivity::class.java)
            startActivity(intent)}
    }

}