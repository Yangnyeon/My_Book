package com.example.mybook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.example.mybook.databinding.ActivityBookAnswerBinding
import com.example.mybook.databinding.ActivityBookBinding

class book_AnswerActivity : AppCompatActivity() {

    private var mBinding : ActivityBookAnswerBinding?= null
    private val binding get() = mBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBookAnswerBinding.inflate(layoutInflater)

        val animation = AnimationUtils.loadAnimation(this, R.anim.page_flip)
        binding.bookAnswer.startAnimation(animation)


        setContentView(binding.root)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.page_flip_reverse, R.anim.page_flip)
    }
}