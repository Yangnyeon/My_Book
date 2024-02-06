package com.example.mybook

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mybook.community.community_Activity
import com.example.mybook.databinding.ActivityMainBinding
import com.example.mybook.reservation.Reservation_Activity
import java.io.*
import java.net.InetAddress
import java.net.Socket


class MainActivity : AppCompatActivity() {

    private var mBinding : ActivityMainBinding ?= null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.production);

 /*       binding.videoView.setVideoURI(videoUri);
        binding.videoView.start();
        binding.videoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
            mp.isLooping = true // 동영상 무한 반복. 반복을 원치 않을 경우 false
        })*/

        binding.bookGogo.setOnClickListener {
            startActivity(Intent(this@MainActivity, book_Activity::class.java))
        }

        binding.communityGogo.setOnClickListener {
            startActivity(Intent(this@MainActivity, community_Activity::class.java))
        }

        binding.reservationGogo.setOnClickListener {
            startActivity(Intent(this@MainActivity, Reservation_Activity::class.java))
        }

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
     /*   binding.videoView.start();
        binding.videoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
            mp.isLooping = true
        })*/
    }


}