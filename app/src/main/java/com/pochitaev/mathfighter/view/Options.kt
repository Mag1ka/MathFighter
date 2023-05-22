package com.pochitaev.mathfighter.view

import android.content.Intent
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.pochitaev.mathfighter.App
import com.pochitaev.mathfighter.databinding.ActivityOptionsBinding
import com.romainpiel.shimmer.Shimmer

class Options : BaseActivity() {
    private lateinit var binding: ActivityOptionsBinding
    val shimmer = Shimmer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()
        shimmer.start(binding.textName)
//Громкость музыки
        val musicSlider = binding.sliderMusic
        musicSlider.valueFrom = 0f
        musicSlider.valueTo = App.getMaxVolume().toFloat()
        val savedVolume = App.getVolume()
        musicSlider.value = savedVolume * App.getMaxVolume().toFloat()
        musicSlider.addOnChangeListener { _, value, _ ->
            val volume = value / App.getMaxVolume().toFloat()
            App.setVolume(volume)
        }
//Громкость SFX

        binding.oback.setOnClickListener {
            val intent = Intent(this@Options, MainActivity::class.java)
            startActivity(intent)
            }
        binding.sliderSound.addOnChangeListener{ _, _, _ ->
            val value = binding.sliderMusic.value
           App.mediaPlayer.setVolume(value,value)
            // Responds to when slider's value is changed
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