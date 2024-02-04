package com.example.mybook.community

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mybook.databinding.ActivityCommunityHolderBinding
import com.example.mybook.databinding.ActivityCommunityWriteBinding
import com.example.mybook.loading_Screen
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class community_Write : AppCompatActivity() {

    private lateinit var binding: ActivityCommunityWriteBinding

    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언

    lateinit var storage: FirebaseStorage

    private val IMAGE_PICK=1111

    var selectImage: Uri?=null

    val itemList = arrayListOf<community_Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommunityWriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        storage = FirebaseStorage.getInstance()

        binding.input.setOnClickListener(View.OnClickListener {

            saveNote()

        })


        binding.imageUpload.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK) //선택하면 무언가를 띄움. 묵시적 호출
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK)
        }




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            selectImage = data?.data
            binding.imageUpload.setImageURI(selectImage)
        }
    }



    private fun saveNote() {
        val title: String = binding.editTextTitle2.text.toString()
        val description: String = binding.editTextDescription2.text.toString()
        val nickname: String = binding.writeNickname.text.toString()
        val now = System.currentTimeMillis()
        val simpleDate = SimpleDateFormat("yyyy-MM-dd k:mm:ss")
        val mDate = Date(now)
        val getTime = simpleDate.format(mDate)
        val password = binding.commentPassword.text.toString()

        val doc = UUID.randomUUID().toString()

        val loadingAnimDialog = loading_Screen(this)

        loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        loadingAnimDialog.show()

        if (selectImage != null) {
            if (title.trim { it <= ' ' }.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                var fileName =
                    SimpleDateFormat("yyyyMMddHHmmss").format(Date()) // 파일명이 겹치면 안되기 떄문에 시년월일분초 지정
                storage.reference.child("image").child(fileName)
                    .putFile(selectImage!!)//어디에 업로드할지 지정
                    .addOnSuccessListener { taskSnapshot -> // 업로드 정보를 담는다
                        taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { it ->
                            //var imageUrl=it.toString()
                            //var photo= Photo(textEt.text.toString(),imageUrl)

                            val data = hashMapOf(
                                "name" to title.toString(),
                                "number" to description.toString(),
                                "com_date" to getTime.toString(),
                                "password" to password.toString(),
                                "doc" to doc,
                                "nickname" to nickname.toString(),
                                "liked" to 0.toLong(),
                                "eye_count" to 0.toLong(),
                                "imageUrl" to it.toString()
                            )

                            db.collection("Contacts")
                                .document(doc)
                                .set(data)
                                .addOnSuccessListener {

                                    loadingAnimDialog.dismiss()

                                    Toast.makeText(
                                        this,
                                        "게시물이 업로드 되었습니다!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    finish()
                                }
                                .addOnFailureListener { exception ->
                                    // 실패할 경우
                                    Toast.makeText(
                                        this,
                                        "실패하였습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }
            }
        } else {
            if (title.trim { it <= ' ' }.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                val data = hashMapOf(
                    "name" to title.toString(),
                    "number" to description.toString(),
                    "com_date" to getTime.toString(),
                    "password" to password.toString(),
                    "doc" to doc,
                    "nickname" to nickname.toString(),
                    "liked" to 0.toLong(),
                    "eye_count" to 0.toLong(),
                    "imageUrl" to ""
                )
                // Contacts 컬렉션에 data를 자동 이름으로 저장
                db.collection("Contacts")
                    .document(doc)
                    .set(data)
                    .addOnSuccessListener {

                        loadingAnimDialog.dismiss()

                        // 성공할 경우
                        Toast.makeText(this, "데이터가 추가되었습니다", Toast.LENGTH_SHORT)
                            .show()

                        finish()

                        //go_board2.putExtra("board_doc", it.toString())
                        // startActivity(go_board2)
                    }
                    .addOnFailureListener { exception ->
                        // 실패할 경우
                        Toast.makeText(this, "실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
            }
        }

    }
}