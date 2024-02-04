package com.example.mybook

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.mybook.databinding.ActivityEnterBinding
import java.net.InetAddress
import java.net.Socket


class activity_enter : AppCompatActivity() {

    private var mBinding : ActivityEnterBinding?= null
    private val binding get() = mBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityEnterBinding.inflate(layoutInflater)

        binding.enterButton.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            val username: String = binding.editText.text.toString()
            intent.putExtra("username", username)
            startActivity(intent)
        }

        setContentView(binding.root)
    }
}