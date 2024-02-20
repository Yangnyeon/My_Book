package com.example.mybook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class IntroScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
        setContentView(R.layout.activity_intro_screen)

        var intent = Intent(this, MainActivity::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)
            startActivity(intent)
            //코루틴을 이용한 인트로화면 설계
        }

    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}