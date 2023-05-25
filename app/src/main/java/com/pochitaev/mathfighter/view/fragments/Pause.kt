package com.pochitaev.mathfighter.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pochitaev.mathfighter.R
import com.pochitaev.mathfighter.databinding.FragmentPauseBinding

class Pause : Fragment() {
    private lateinit var binding: FragmentPauseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentPauseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pause, container, false)
    }


}