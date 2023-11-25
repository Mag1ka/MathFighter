package com.pochitaev.mathfighter.view

import android.content.Intent
import android.os.Bundle
import android.util.Log

import com.google.android.gms.games.PlayGames
import com.google.android.gms.tasks.Task
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
        authCheck()
        binding.oback.setOnClickListener {
            val intent = Intent(this@Options, MainActivity::class.java)
            startActivity(intent)
        }
        binding.signIn.setOnClickListener { authCheck()

        }
    }
    private fun authCheck() {
        val gamesSignInClient = PlayGames.getGamesSignInClient(this)
        gamesSignInClient
            .requestServerSideAccess("216836792617-dilh6jvv6ak5203bei6q5kg2bffouehc.apps.googleusercontent.com",  /* forceRefreshToken= */false)
            .addOnCompleteListener { task: Task<String?> ->
                if (task.isSuccessful) {
                    val serverAuthToken = task.result
                    // Send authentication code to the backend game server to be
                    // exchanged for an access token and used to verify the player
                    // via the Play Games Services REST APIs.
                } else {
                    val exception = task.exception
                    Log.e("AuthCheck", "Failed to retrieve authentication code", exception)
                    // Failed to retrieve authentication code.
                }
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

