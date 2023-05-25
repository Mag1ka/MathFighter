package com.pochitaev.mathfighter.view

import com.pochitaev.mathfighter.databinding.ActivityGameBinding
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.pochitaev.mathfighter.R
import com.pochitaev.mathfighter.data.entity.ShopEntity
import com.pochitaev.mathfighter.data.repository.CoinRepo
import com.pochitaev.mathfighter.data.repository.ShopRepo
import com.pochitaev.mathfighter.view.fragments.Pause
import com.romainpiel.shimmer.Shimmer
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView


class Game : BaseActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var cd: GifDrawable
    val repo: ShopRepo by lazy { ShopRepo(this) }
    val coinRepo: CoinRepo by lazy { CoinRepo(this) }
    private lateinit var cTimer: CountDownTimer
    var round = 0
    var score = 0
    var answer = 0
    var combo = 100
    var bScore = 100
    var bCoins = 100
    var healthMax = 100
    var healthCurrent = 100
    var reward = 0
    var revive = 0
    var time = 180000
    var alertRes = 0
    val shimmer = Shimmer()
    var currentTime = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bCheck()
        shim()
        timer(time)
        numpad()
        formulaGen()
        pause()

        val healthPercent = (healthCurrent * 100) / healthMax
        binding.hBar.setProgressPercentage(healthPercent.toDouble(), true)
        binding.hbText.text = getString(R.string.health) + " " + "$healthCurrent/$healthMax"


    }

    private fun shim() {
        shimmer.start(binding.but0)
        shimmer.start(binding.but1)
        shimmer.start(binding.but2)
        shimmer.start(binding.but3)
        shimmer.start(binding.but4)
        shimmer.start(binding.but5)
        shimmer.start(binding.but6)
        shimmer.start(binding.but7)
        shimmer.start(binding.but8)
        shimmer.start(binding.but9)
        shimmer.start(binding.hbText)
        shimmer.start(binding.sign)
        shimmer.start(binding.firstNum)
        shimmer.start(binding.secondNum)
        shimmer.start(binding.timer)
        shimmer.start(binding.answer)
        binding.answer.text = ""

    }
    private fun timer(qTime: Int) {
        cTimer = object : CountDownTimer(qTime.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                binding.timer.text =
                    (millisUntilFinished / 1000).toString() + getString(R.string.timer_sec)
                currentTime = ((millisUntilFinished / 1000).toInt())
            }

            override fun onFinish() {
                if (currentTime < 1) {gameEnd()}
            }
        }.start()

    }
    private fun formulaGen() {
        answer = 0
        val a = Math.random()
        val b = Math.random()

        if (round < 5) {
            binding.firstNum.text = (a * 10).toInt().toString()
            binding.sign.text = "+"
            binding.secondNum.text = (b * 10).toInt().toString()
        }
        if (round in 5..9) {
            binding.firstNum.text = (a * 100).toInt().toString()
            binding.sign.text = "+"
            binding.secondNum.text = (b * 10).toInt().toString()
        }
        if (round in 10..14) {
            binding.firstNum.text = (a * 100).toInt().toString()
            binding.sign.text = "+"
            binding.secondNum.text = (b * 10).toInt().toString()
        }
        if (round in 15..19) {
            binding.firstNum.text = (a * 10).toInt().toString()
            binding.sign.text = "*"
            binding.secondNum.text = (b * 10).toInt().toString()
        }
        if (round in 20..24) {
            binding.firstNum.text = (a * 100).toInt().toString()
            binding.sign.text = "*"
            binding.secondNum.text = (b * 10).toInt().toString()
        }
        if (round in 25..29) {
            binding.firstNum.text = (a * 100).toInt().toString()
            binding.sign.text = "*"
            binding.secondNum.text = (b * 100).toInt().toString()
        }
        if (round > 30) {
            binding.firstNum.text = (a * 1000).toInt().toString()
            binding.sign.text = "*"
            binding.secondNum.text = (b * 1000).toInt().toString()
        }


    }
    private fun numpad() {
        binding.but1.setOnClickListener {
            binding.answer.text = binding.answer.text.toString() + "1"
        }
        binding.but2.setOnClickListener {
            binding.answer.text = binding.answer.text.toString() + "2"
        }
        binding.but3.setOnClickListener {
            binding.answer.text = binding.answer.text.toString() + "3"
        }
        binding.but4.setOnClickListener {
            binding.answer.text = binding.answer.text.toString() + "4"
        }
        binding.but5.setOnClickListener {
            binding.answer.text = binding.answer.text.toString() + "5"
        }
        binding.but6.setOnClickListener {
            binding.answer.text = binding.answer.text.toString() + "6"
        }
        binding.but7.setOnClickListener {
            binding.answer.text = binding.answer.text.toString() + "7"
        }
        binding.but8.setOnClickListener {
            binding.answer.text = binding.answer.text.toString() + "8"
        }
        binding.but9.setOnClickListener {
            binding.answer.text = binding.answer.text.toString() + "9"
        }
        binding.but0.setOnClickListener {
            binding.answer.text = binding.answer.text.toString() + "0"
        }
        binding.butBackspace.setOnClickListener {
            if (binding.answer.text.toString().isNotEmpty())
                binding.answer.text = binding.answer.text.toString()
                    .substring(0, binding.answer.text.toString().length - 1)
        }
        binding.butEnter.setOnClickListener {
            if (binding.answer.text.toString().isNotEmpty()) {
                ansCheck(binding.answer.text.toString().toInt())
            } else Toast.makeText(this, getString(R.string.aEmpty), Toast.LENGTH_SHORT).show()
        }

    }
    private fun ansCheck(ans: Int) {
        when (binding.sign.text.toString()) {
            "+" -> answer =
                binding.firstNum.text.toString().toInt() + binding.secondNum.text.toString().toInt()
            "*" -> answer =
                binding.firstNum.text.toString().toInt() * binding.secondNum.text.toString().toInt()
        }

        if (ans == answer) {
            if (combo < 150) {
                attack()
            } else comboAttack()

            binding.answer.text = ""
            formulaGen()
            score += (10 * combo) / 100
            reward += (5 * combo) / 100
            combo += 10
            round++
            binding.scText.text = getString(R.string.score_0) + score.toString()
            binding.cText.text = reward.toString()

        } else {
            block()
            Toast.makeText(this, getString(R.string.wrong) + answer, Toast.LENGTH_SHORT).show()
            binding.answer.text = ""
            formulaGen()
            round++
            healthCurrent -= 50
            combo = 100
            val healthPercent = (healthCurrent * 100) / healthMax
            binding.hBar.setProgressPercentage(healthPercent.toDouble(), true)
            binding.hbText.text = getString(R.string.health) + "$healthCurrent/$healthMax"
            if (healthCurrent < 1) {
                gameEnd()
            }

        }

    }
    private fun bCheck() {
        val hBonus = mutableListOf<ShopEntity>()
        val tBonus = mutableListOf<ShopEntity>()
        val cBonus = mutableListOf<ShopEntity>()
        val sBonus = mutableListOf<ShopEntity>()
        val rBonus = mutableListOf<ShopEntity>()
        val allBonus = repo.getCategories()
        allBonus.forEach {
            when (it.name) {
                "Health" -> if (it.isSolded) {
                    hBonus.add(it)
                }
                "Time" -> if (it.isSolded) {
                    tBonus.add(it)
                }
                "Gold" -> if (it.isSolded) {
                    cBonus.add(it)
                }
                "Score" -> if (it.isSolded) {
                    sBonus.add(it)
                }
                "Revive" -> if (it.isSolded) {
                    rBonus.add(it)
                }
            }
        }
//HealthBonus
        if (hBonus.isNotEmpty()) {
            val index = hBonus.lastIndex
            healthCurrent = hBonus[index].value!!
            healthMax = hBonus[index].value!!
        }
//TimeBonus
        if (tBonus.isNotEmpty()) {
            val index = tBonus.lastIndex
            time += tBonus[index].value!!
        }
//CoinsBonus
        if (cBonus.isNotEmpty()) {
            val index = cBonus.lastIndex
            bCoins = cBonus[index].value!!
        }
//ScoreBonus
        if (sBonus.isNotEmpty()) {
            val index = sBonus.lastIndex
            bScore = sBonus[index].value!!
        }
//Revive
        if (rBonus.isNotEmpty()) {
            val index = rBonus.lastIndex
            revive = rBonus[index].value!!
        }


    }
    private fun attack() {
        val charIdle = binding.idle
        val attackG = binding.attack.drawable as GifDrawable
        val attackV = binding.attack
        val enemyIdle = binding.enemyIdle
        val enemyDeathG = binding.enemyDeath.drawable as GifDrawable
        val enemyDeathV = binding.enemyDeath
        //First step of animation
        //Hero
        charIdle.visibility = View.INVISIBLE
        attackV.visibility = View.VISIBLE
        val mp = MediaPlayer.create(this, R.raw.heavy_hit)
        mp.start()
        attackG.reset()
        //Enemy
        Handler().postDelayed({
            enemyIdle.visibility = View.INVISIBLE
            enemyDeathV.visibility = View.VISIBLE
            enemyDeathG.reset()
        }, 950)
        Handler().postDelayed({
            charIdle.visibility = View.VISIBLE
            attackV.visibility = View.INVISIBLE
            enemyIdle.visibility = View.VISIBLE
            enemyDeathV.visibility = View.INVISIBLE
        }, 1600)
    }
    private fun comboAttack() {
        val charIdle = binding.idle
        val attackG = binding.comboAttack.drawable as GifDrawable
        val attackV = binding.comboAttack
        val enemyIdle = binding.enemyIdle
        val enemyDeathG = binding.enemyDeath.drawable as GifDrawable
        val enemyDeathV = binding.enemyDeath
        //First step of animation
        //Hero
        charIdle.visibility = View.INVISIBLE
        attackV.visibility = View.VISIBLE
        attackG.reset()
        //Enemy
        Handler().postDelayed({
            enemyIdle.visibility = View.INVISIBLE
            enemyDeathV.visibility = View.VISIBLE
            enemyDeathG.reset()
        }, 750)
        //Second step of animation (Return back to old views)
        Handler().postDelayed({
            charIdle.visibility = View.VISIBLE
            attackV.visibility = View.INVISIBLE
            enemyIdle.visibility = View.VISIBLE
            enemyDeathV.visibility = View.INVISIBLE
        }, 1600)
    }
    private fun block() {
        val cIdle = binding.idle
        val eIdle = binding.enemyIdle
        val cBlockG = binding.block.drawable as GifDrawable
        val cBlockV = binding.block
        val eAttackG = binding.enemyAttack.drawable as GifDrawable
        val eAttackV = binding.enemyAttack
        eIdle.visibility = View.INVISIBLE
        eAttackV.visibility = View.VISIBLE
        eAttackG.reset()
        Handler().postDelayed({
            cIdle.visibility = View.INVISIBLE
            cBlockV.visibility = View.VISIBLE
            val mp = MediaPlayer.create(this, R.raw.get_hit)
            mp.start()
            cBlockG.reset()
        }, 350)
        Handler().postDelayed({
            eIdle.visibility = View.VISIBLE
            eAttackV.visibility = View.INVISIBLE
            cIdle.visibility = View.VISIBLE
            cBlockV.visibility = View.INVISIBLE
        }, 800)


    }
    private fun gameEnd() {

        if (alertRes == 0) {
            adS()
            cTimer.cancel()
            alertRes++
            binding.mainScreen.animate().alpha(0.0F).setDuration(1000).withEndAction {
                binding.adRev.animate().alpha(1.0F).duration = 1000
                cd = findViewById<GifImageView>(R.id.char_death).drawable as GifDrawable
                val cd2 = findViewById<GifImageView>(R.id.char_death)
                Handler().postDelayed({
                    cd2.visibility = View.VISIBLE
                    cd.reset()
                }, 300)
            }

            val adYes = findViewById<Button>(R.id.y_butt)
            val adNo = findViewById<Button>(R.id.n_butt)
            adYes.setOnClickListener {
                adE()
                alertRes = 1
                healthCurrent = healthMax / 2
                timer(time / 2)
                val healthPercent = (healthCurrent * 100) / healthMax
                binding.hBar.setProgressPercentage(healthPercent.toDouble(), true)
                binding.hbText.text = getString(R.string.health) + "$healthCurrent/$healthMax"
//anim
                binding.adRev.animate().alpha(0.0F).setDuration(1000)
                    .withEndAction { binding.mainScreen.animate().alpha(1.0F).duration = 1000 }

            }
            adNo.setOnClickListener {
                adE()
                binding.adRev.animate().alpha(0.0F).setDuration(1000)
                    .withEndAction { binding.mainScreen.animate().alpha(1.0F).duration = 1000 }

                gameEnd()
            }

        } else {
            cTimer.cancel()

            binding.mainScreen.animate().alpha(0.0F).duration = 1000
            Handler().postDelayed({
                val coinsPack = reward * bCoins / 100
                val scorePack = score * bScore / 100
                val intent = Intent(this@Game, GameOver::class.java)
                intent.putExtra("coinsPack", coinsPack)
                intent.putExtra("scorePack", scorePack)
                startActivity(intent)
            }, 1000)
        }

    }
    private fun pause() {
        binding.pauseButt.setOnClickListener {
            binding.mainScreen.animate().alpha(0.0F).setDuration(1000).withEndAction {
                binding.pause.animate().alpha(1.0F).duration = 1000
                pauseS()
                cTimer.cancel()
                val cont = findViewById<Button>(R.id.cont)
                val quit = findViewById<Button>(R.id.quit)
                cont.setOnClickListener {
                    timer((currentTime+2)*1000)
                    binding.pause.animate().alpha(0.0F).setDuration(1000).withEndAction {
                        binding.mainScreen.animate().alpha(1.0F).duration = 1000
                        pauseE()
                    }

                }
                quit.setOnClickListener {
                    binding.pause.animate().alpha(0.0F).duration = 1000
                    Handler().postDelayed({
                        val coinsPack = reward * bCoins / 100
                        val scorePack = score * bScore / 100
                        val intent = Intent(this@Game, GameOver::class.java)
                        intent.putExtra("coinsPack", coinsPack)
                        intent.putExtra("scorePack", scorePack)
                        startActivity(intent)
                    }, 1000)
                }

            }
        }
    }
    private fun disB(buttons: List<View>) {
        buttons.forEach { button ->
            button.isEnabled = false
        }
    }
    private fun enB(buttons: List<View>) {
        buttons.forEach { button ->
            button.isEnabled = true
        }
    }
    private fun pauseS(){
        val mainB : List<View> = listOf(binding.but0,
            binding.but1,
            binding.but2,
            binding.but3,
            binding.but4,
            binding.but5,
            binding.but6,
            binding.but7,
            binding.but8,
            binding.but9,
            binding.pauseButt,
            binding.butBackspace,
            binding.butEnter)
        val adRevB : List<View> = listOf(findViewById(R.id.y_butt), findViewById(R.id.n_butt))
        val pauseB : List<View> = listOf(findViewById(R.id.cont), findViewById(R.id.quit))
        disB(mainB)
        disB(adRevB)
        enB(pauseB)
    }
    private fun pauseE(){
        val mainB : List<View> = listOf(binding.but0,
            binding.but1,
            binding.but2,
            binding.but3,
            binding.but4,
            binding.but5,
            binding.but6,
            binding.but7,
            binding.but8,
            binding.but9,
            binding.pauseButt,
            binding.butBackspace,
            binding.butEnter)
        val adRevB : List<View> = listOf(findViewById(R.id.y_butt), findViewById(R.id.n_butt))
        val pauseB : List<View> = listOf(findViewById(R.id.cont), findViewById(R.id.quit))
        disB(pauseB)
        disB(adRevB)
        enB(mainB)
    }
    private fun adS(){
        val mainB : List<View> = listOf(binding.but0,
            binding.but1,
            binding.but2,
            binding.but3,
            binding.but4,
            binding.but5,
            binding.but6,
            binding.but7,
            binding.but8,
            binding.but9,
            binding.pauseButt,
            binding.butBackspace,
            binding.butEnter)
        val adRevB : List<View> = listOf(findViewById(R.id.y_butt), findViewById(R.id.n_butt))
        val pauseB : List<View> = listOf(findViewById(R.id.cont), findViewById(R.id.quit))
        disB(pauseB)
        disB(mainB)
        enB(adRevB)
    }
    private fun adE(){
        val mainB : List<View> = listOf(binding.but0,
            binding.but1,
            binding.but2,
            binding.but3,
            binding.but4,
            binding.but5,
            binding.but6,
            binding.but7,
            binding.but8,
            binding.but9,
            binding.pauseButt,
            binding.butBackspace,
            binding.butEnter)
        val adRevB : List<View> = listOf(findViewById(R.id.y_butt), findViewById(R.id.n_butt))
        val pauseB : List<View> = listOf(findViewById(R.id.cont), findViewById(R.id.quit))
        disB(pauseB)
        disB(adRevB)
        enB(mainB)
    }
}
//    private fun res(){
//
//        healthCurrent = healthMax / 2
//        timer(time / 2)
//        val healthPercent = (healthCurrent * 100) / healthMax
//        binding.hBar.setProgressPercentage(healthPercent.toDouble(), true)
//        binding.hbText.text = getString(R.string.health) + "$healthCurrent/$healthMax"
//
//    }

