package com.rahul.titan.myspot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rahul.titan.myspot.databinding.ActivityProfileScreenBinding

class profile_screen : AppCompatActivity() {
    private lateinit var binding : ActivityProfileScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}