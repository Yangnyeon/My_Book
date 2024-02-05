package com.example.mybook

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mybook.databinding.ActivityBookBinding
import com.google.firebase.firestore.FirebaseFirestore

class book_Activity : AppCompatActivity(), OnItemClick, book_Adapter.OnItemClick  {

    private var mBinding: ActivityBookBinding? = null
    private val binding get() = mBinding!!

    val content_List = ArrayList<String>()

    private lateinit var book_ViewModelVar: book_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBookBinding.inflate(layoutInflater)

        binding.gopage.setOnClickListener {
            startScaleDownAnimation()
        }


        FirebaseFirestore.getInstance().collection("Book")
            .get()
            .addOnSuccessListener { result ->

                val contentList = mutableListOf<String>() // 임시 리스트 생성

                for (document in result) {
                    contentList.add(document.getString("Book_Content").toString())
                }

                // 성공 리스너 내에서 랜덤으로 선택하고 UI에 설정
                binding.bookContent.text = contentList.random()

            }
            .addOnFailureListener {
                Toast.makeText(this@book_Activity, "실패", Toast.LENGTH_SHORT).show()
            }


        setContentView(binding.root)
    }

    private fun startScaleDownAnimation() {
        var pagingStartAnim = AnimationUtils.loadAnimation(this, R.anim.page_flip)
        var pagingEndAnim = AnimationUtils.loadAnimation(this, R.anim.scale_up_reverse_0_1)

        pagingEndAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

                binding.bookAnswer.visibility = View.VISIBLE

            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })
        pagingStartAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.bookMain.startAnimation(pagingEndAnim)
                binding.pageTop.setBackgroundResource(R.drawable.round_solid_white_border_d8d8d8)
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })

        binding.contentReservation.setOnClickListener {
            val memo_dialog = book_Dailog(this,this, binding.bookContent.text.toString())
            memo_dialog.show()
        }

        val repository = book_Repository(this.application)

        val viewModelFactory = book_Factory(repository)
        book_ViewModelVar = ViewModelProvider(this, viewModelFactory)[book_ViewModel::class.java]


        binding.bookMain.startAnimation(pagingStartAnim)
    }

    override fun deleteTodo(book: Book_Model) {
        book_ViewModelVar.delete(book)
    }

    override fun check_memo(content: String, dialog: Dialog) {
        book_ViewModelVar.insert(Book_Model(content))
        Toast.makeText(this,"저장되었습니다...", Toast.LENGTH_SHORT).show()
        dialog.dismiss()
    }



}