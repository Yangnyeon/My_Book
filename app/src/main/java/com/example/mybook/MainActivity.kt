package com.example.mybook

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mybook.advice.*
import com.example.mybook.community.community_Activity
import com.example.mybook.databinding.ActivityMainBinding
import com.example.mybook.reservation.Reservation_Activity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.*


class MainActivity : AppCompatActivity() {

    private var mBinding : ActivityMainBinding ?= null
    private val binding get() = mBinding!!

    private lateinit var viewModel : advice_ViewModel

    val handler = Handler(Looper.getMainLooper()) {
        setPage()
        true
    }

    var currentPosition = 0

    var advice_List : ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(2000)
                setPage()
            }
        } //뷰페이저

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

        val repository = advice_Repository()
        val viewModelFactory = advice_Factory_ViewModel(repository)

        viewModel = ViewModelProvider(this,viewModelFactory).get(advice_ViewModel::class.java)




        viewModel.getAdvice()

        viewModel.getAdvice2()

        viewModel.getAdvice3()

            viewModel.myResponse.observe(this, Observer { it ->
                Log.d("확인", it.toString())
                if (it.isSuccessful) {
                    val body = it.body()
                    body?.let {
                        advice_List.add(it.slip!!.advice.toString())
                        setAdapter(advice_List,it.slip!!.advice.toString())
                        Log.d("제발아아", advice_List.toString())
                    }
                }
                else{
                    Toast.makeText(this@MainActivity,it.code(), Toast.LENGTH_SHORT).show()
                }
            })

        Log.d("제발", advice_List.toString())

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setPage() {
        if(currentPosition == 3 ) {
            currentPosition = 0
        }
        if(binding.viewpager11 != null) {
            binding.viewpager11.setCurrentItem(currentPosition, true)
        }

        currentPosition += 1
    }

    private fun setAdapter(items: ArrayList<String>?,advice_Text : String) {

        val mAdapter = advice_Adapter(items, advice_Text)
        binding.viewpager11.adapter = mAdapter
    }


}