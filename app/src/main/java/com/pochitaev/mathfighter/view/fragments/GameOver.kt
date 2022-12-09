package com.pochitaev.mathfighter.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pochitaev.mathfighter.R
import com.pochitaev.mathfighter.databinding.FragmentAdRevBinding
import com.pochitaev.mathfighter.databinding.FragmentGameOverBinding

class GameOver : Fragment() {
    private lateinit var binding: FragmentGameOverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentGameOverBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_over, container, false)
    }


}