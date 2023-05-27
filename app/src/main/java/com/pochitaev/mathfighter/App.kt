package com.pochitaev.mathfighter

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.MediaPlayer

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
        lateinit var appContext: Context
            private set
        lateinit var mediaPlayer: MediaPlayer
            private set
        lateinit var sharedPreferences: SharedPreferences
            private set

        const val PREF_VOLUME = "pref_volume"
        const val PREF_VOLUME2 = "pref_volume2"
        fun setVolume(volume: Float) {
            mediaPlayer.setVolume(volume, volume)
            // Сохранение уровня громкости в SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putFloat(PREF_VOLUME, volume)
            editor.apply()
        }
        fun getVolume(): Float {
            // Загрузка уровня громкости из SharedPreferences
            return sharedPreferences.getFloat(PREF_VOLUME, 1.0f)
        }
        fun setVolume2(volume: Float) {
            val editor = sharedPreferences.edit()
            editor.putFloat(PREF_VOLUME2, volume)
            editor.apply()
        }
        fun getVolume2(): Float {
            // Загрузка уровня громкости из SharedPreferences
            return sharedPreferences.getFloat(PREF_VOLUME2, 1.0f)
        }

        fun getMaxVolume(): Int {
            val audioManager = appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        appContext = applicationContext
        initializeMediaPlayer()
    }
    private fun initializeMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.bg_music)
        mediaPlayer.setVolume(getVolume(), getVolume())
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

}
