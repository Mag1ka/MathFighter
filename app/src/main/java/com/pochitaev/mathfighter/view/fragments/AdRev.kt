package com.pochitaev.mathfighter.view.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.pochitaev.mathfighter.R
import com.pochitaev.mathfighter.databinding.ActivityLeaderboardsBinding
import com.pochitaev.mathfighter.databinding.FragmentAdRevBinding
import kotlinx.coroutines.delay
import pl.droidsonroids.gif.GifDrawable


class AdRev : Fragment() {
    private lateinit var binding: FragmentAdRevBinding
    private lateinit var cd: GifDrawable
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentAdRevBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        cd = binding.charDeath.drawable as GifDrawable


        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ad_rev, container, false)
    }

}