package com.example.mybook

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.mybook.databinding.ActivityBookDailogBinding
import com.example.mybook.reservation.Reservation_Activity

class book_Dailog(context: Context, OnItemClick: OnItemClick, var bookContent: String) : Dialog(context), View.OnClickListener  {

    private lateinit var binding : ActivityBookDailogBinding

    var ItemClick : OnItemClick ?= null

    init {
        this.ItemClick = OnItemClick
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_dailog)

        binding = ActivityBookDailogBinding.inflate(layoutInflater)




        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.memoBtn.setOnClickListener {
            val userInput = binding.bookEdit.text.toString()
            if (userInput.isEmpty()) {
                // 입력이 비어있을 때 처리
                Toast.makeText(context, "질문을 입력해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                // 입력이 비어있지 않을 때 처리
                this.ItemClick?.check_memo(bookContent, userInput, this)
            }
        }


        setContentView(binding.root)

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}