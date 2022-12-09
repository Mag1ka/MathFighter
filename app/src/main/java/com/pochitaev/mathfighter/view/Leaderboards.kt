package com.pochitaev.mathfighter.view

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

class Leaderboards : AppCompatActivity(), AnimationListener {
    private lateinit var binding: ActivityLeaderboardsBinding
    private lateinit var gifDrawable: Gif

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardsBinding.inflate(layoutInflater)
        gifDrawable = binding.enemy.drawable as Gif

        setContentView(binding.root)
        hideSystemBars()
        buttons()
        gifDrawable.addAnimationListener(this)

    }

    private fun buttons() {

        binding.start.setOnClickListener{gifDrawable.start()}
        binding.stop.setOnClickListener{gifDrawable.stop()}
        binding.recycle.setOnClickListener{gifDrawable.reset()
                                            gifDrawable.start()}
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

    override fun onAnimationCompleted(loopNumber: Int) {
        gifDrawable.stop()
    }

}