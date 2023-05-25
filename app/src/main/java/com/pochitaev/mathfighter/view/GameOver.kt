package com.pochitaev.mathfighter.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.pochitaev.mathfighter.R
import com.pochitaev.mathfighter.data.repository.CoinRepo
import com.pochitaev.mathfighter.databinding.ActivityGameOverBinding
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView


class GameOver : BaseActivity() {

    private lateinit var binding: ActivityGameOverBinding
    private lateinit var cd: GifDrawable
    val coinRepo: CoinRepo by lazy { CoinRepo(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        binding.frameLayout.startAnimation(fadeInAnimation)
        gText()
        hAnim()
        binding.goButt.setOnClickListener {
            val intent = Intent(this@GameOver, MainActivity::class.java)
            coinRepo.reward(intent.getIntExtra("coinsPack", 0))
            startActivity(intent)
        }
    }
    private fun hAnim(){
        cd = findViewById<GifImageView>(R.id.char_death2).drawable as GifDrawable
        val cd2 = findViewById<GifImageView>(R.id.char_death2)
        Handler().postDelayed({
            cd2.visibility = View.VISIBLE
            cd.reset()
        }, 300)
    }
    private fun gText(){
        val coins = intent.getIntExtra("coinsPack", 0)
        val score = intent.getIntExtra("scorePack", 0)
        val ctext = binding.goReward.text.toString()
        binding.goReward.setText(ctext+ " " +coins)
        binding.goScore.setText("You earned "+ score + " score")
    }
}