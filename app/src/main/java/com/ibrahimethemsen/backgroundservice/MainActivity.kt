package com.ibrahimethemsen.backgroundservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ibrahimethemsen.backgroundservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.startBtn.setOnClickListener {
            startService(Intent(this, NewService::class.java))
        }
        binding.finishBtn.setOnClickListener {
            stopService(Intent(this, NewService::class.java))
        }

    }


}