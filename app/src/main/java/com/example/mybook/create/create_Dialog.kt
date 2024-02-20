package com.example.mybook.create

import android.app.Dialog
import android.os.Bundle
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.example.mybook.OnItemClick
import com.example.mybook.databinding.ActivityCreateDialogBinding

class create_Dialog(context: Context, OnItemClick: OnItemClick) : Dialog(context), View.OnClickListener  {

    private var _binding : ActivityCreateDialogBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    var ItemClick : OnItemClick ?= null

    init {
        this.ItemClick = OnItemClick
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogClose.setOnClickListener {
            this.ItemClick?.close_Dialog(this)
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}