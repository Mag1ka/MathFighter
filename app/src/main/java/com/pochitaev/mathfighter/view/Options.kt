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
        shimmer.start(binding.textName)
        bgm()
        sfx()
        binding.oback.setOnClickListener {
            val intent = Intent(this@Options, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun bgm() {
        val musicSlider = binding.sliderMusic
        musicSlider.valueFrom = 0f
        musicSlider.valueTo = App.getMaxVolume().toFloat()
        val savedVolume = App.getVolume()
        musicSlider.value = savedVolume * App.getMaxVolume().toFloat()
        musicSlider.addOnChangeListener { _, value, _ ->
            val volume = value / App.getMaxVolume().toFloat()
            App.setVolume(volume)
        }
    }
        private fun sfx() {
            val sliderSound = binding.sliderSound
            sliderSound.valueFrom = 0f
            sliderSound.valueTo = App.getMaxVolume().toFloat()
            val savedVolume2 = App.getVolume2()
            sliderSound.value = savedVolume2 * App.getMaxVolume().toFloat()
            sliderSound.addOnChangeListener { _, value, _ ->
                val volume = value / App.getMaxVolume().toFloat()
                App.setVolume2(volume)
            }

        }
    }

