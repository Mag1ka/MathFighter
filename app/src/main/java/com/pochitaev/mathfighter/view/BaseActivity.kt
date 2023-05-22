package com.pochitaev.mathfighter.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pochitaev.mathfighter.App

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        App.mediaPlayer.start()
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            App.mediaPlayer.stop()
            App.mediaPlayer.release()
        } else {
            App.mediaPlayer.pause()
        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}