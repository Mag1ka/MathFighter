package com.pochitaev.mathfighter.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.pochitaev.mathfighter.R
import com.pochitaev.mathfighter.databinding.ActivityLeaderboardsBinding
import kotlinx.coroutines.delay
import pl.droidsonroids.gif.AnimationListener
import pl.droidsonroids.gif.GifImageView
import pl.droidsonroids.gif.GifDrawable as Gif

class Leaderboards : BaseActivity(){
    private lateinit var binding: ActivityLeaderboardsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            val intent = Intent(this@Leaderboards, MainActivity::class.java)
            startActivity(intent)
        }
    }
}