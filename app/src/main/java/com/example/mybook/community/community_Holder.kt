package com.example.mybook.community

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.mybook.R
import com.example.mybook.community.Comment.comment_Data
import com.example.mybook.community.Comment.comment_ListAdapter
import com.example.mybook.databinding.ActivityCommunityHolderBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class community_Holder : AppCompatActivity() {

    private lateinit var binding: ActivityCommunityHolderBinding

    val db = FirebaseFirestore.getInstance()

    val itemList2 = arrayListOf<comment_Data>()

    var adapter = comment_ListAdapter(itemList2, this)

    val itemList = arrayListOf<community_Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommunityHolderBinding.inflate(layoutInflater)
        val view = binding.root
        //setContentView(R.layout.activity_cloud_firestore)
        setContentView(view)


        binding.recyclerViewCommunityComment.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewCommunityComment.adapter = adapter




        //


        intent = intent // 인텐트 받아오기

        val title = intent.getStringExtra("board_title") //Adapter에서 받은 키값 연결
        val date = intent.getStringExtra("board_date")
        val content = intent.getStringExtra("board_content")
        val holder_doc = intent.getStringExtra("board_doc")
        val password = intent.getStringExtra("board_password")
        val like_count = intent.getStringExtra("board_liked")
        val nickname = intent.getStringExtra("board_Nickname")

        // board_title.setText(title)
        // board_date.setText(content)
        // board_content.setText(date)
        // board_nickname.text = nickname.toString()
        //likes.text = like_count.toString()

        var settings: SharedPreferences = getSharedPreferences("like_tmp", MODE_PRIVATE)

        var editor: SharedPreferences.Editor = settings.edit()

        if(settings.getBoolean(holder_doc.toString(), false))
        {
            binding.notliked.visibility = View.INVISIBLE
            binding.liked.visibility = View.VISIBLE
        }
        else
        {
            binding.notliked.visibility = View.VISIBLE
            binding.liked.visibility = View.INVISIBLE
        }

        db.collection("Contacts")
            .document(holder_doc.toString())
            .get()
            .addOnSuccessListener { result ->
                try {
                    with(result) {

                        binding.boardTitle.text = "${getString("name")}"
                        binding.boardDate.text = "${getString("com_date")}"
                        binding.boardContent.text = "${getString("number")}"
                        binding.boardNickname.text = "${getString("nickname")}"
                        binding.likes.text = "${getLong("liked")}"
                        binding.eyeHolderCount.text = "조회수 : ${getLong("eye_count")}"

                        /*
                        Glide.with(this@Community_holder)
                            .load("${getString("imageUrl")}")
                            .fallback(null)
                            .into(real_holder_image)

                         */

                        binding.realHolderImage
                            .load("${getString("imageUrl")}"){
                                placeholder(null)
                                error(null)
                            }


                    }
                } catch (e: Exception) {
                    Toast.makeText(this, e.toString() , Toast.LENGTH_SHORT).show()
                }
            }



        val go_comment_delelte = Intent(this, community_Holder::class.java)
        go_comment_delelte.putExtra("board_doc", holder_doc)





        //댓글출력
        db.collection("Contacts")
            .document(holder_doc.toString())
            .collection("Comment")// 작업할 컬렉션
            .orderBy("Date", Query.Direction.ASCENDING)
            .get() // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                itemList2.clear()
                for (document in result) {  // 가져온 문서들은 result에 들어감
                    val item2 =
                        comment_Data(
                            document["Comment"] as String,
                            document["Date"] as String,
                            document["Doc"] as String,
                            document["comment_password"] as String,
                            document["content_doc"] as String,
                            document["Content_nickname"] as String,
                            document["Comment_liked"] as Long
                        )
                    itemList2.add(item2)
                }
                adapter.notifyDataSetChanged()// 리사이클러 뷰 갱신
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.w("TAG", "Error getting documents: $exception")
            }

        //



        //댓글입력

        binding.commnetButton.setOnClickListener {

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.activity_comment__dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

            //mBuilder.show()

            val mAlertDialog = mBuilder.show()

            val comment_upload = mDialogView.findViewById<CardView>(R.id.comment_upload)
            val comment_cancle = mDialogView.findViewById<CardView>(R.id.comment_cancle)
            val edit_nickname = mDialogView.findViewById<EditText>(R.id.edit_nickname)
            val edit_password = mDialogView.findViewById<EditText>(R.id.edit_password)

            comment_upload.setOnClickListener {

                var comment_edit = binding.commentEdit.text.toString()
                val currentTime: Long = System.currentTimeMillis()
                val simpleDate = SimpleDateFormat("yyyy-MM-dd k:mm:ss")
                val mDate: Date = Date(currentTime)
                val getTime = simpleDate.format(mDate)
                val content_doc = holder_doc.toString()
                val content_nickname =  edit_nickname.text.toString()

                val doc = UUID.randomUUID().toString()

                val data = hashMapOf(
                    "Comment" to comment_edit,
                    "Date" to getTime.toString(),
                    "Doc" to doc,
                    "comment_password" to edit_password.text.toString(),
                    "content_doc" to content_doc,
                    "Content_nickname" to content_nickname,
                    "Comment_liked" to 0.toLong()
                )

                db.collection("Contacts")
                    .document(holder_doc.toString())
                    .collection("Comment")
                    .document(doc.toString())
                    .set(data)
                    .addOnSuccessListener {
                        // 성공할 경우
                        Toast.makeText(this, "데이터가 추가되었습니다", Toast.LENGTH_SHORT).show()

                        update()

                        mAlertDialog.dismiss()


                        //go_board2.putExtra("board_doc", it.toString())
                        // startActivity(go_board2)
                    }
                    .addOnFailureListener { exception ->
                        // 실패할 경우

                        Log.w("MainActivity", "Error getting documents: $exception")
                    }
            }

            comment_cancle.setOnClickListener {
                mAlertDialog.dismiss()
            }



        }



        binding.liked.setOnClickListener {
            binding.notliked.visibility = View.VISIBLE
            binding.liked.visibility = View.INVISIBLE

            db.collection("Contacts")
                .document(holder_doc.toString())
                .update("liked", FieldValue.increment(-1))
                .addOnSuccessListener { result ->

                    db.collection("Contacts")
                        .document(holder_doc.toString())
                        .get()
                        .addOnSuccessListener { result ->
                            try {
                                with(result) {

                                    binding.likes.text = "${getLong("liked")}"
                                    editor.remove(holder_doc.toString())
                                    editor.commit()

                                }
                            } catch (e: Exception) {
                                Toast.makeText(this, e.toString() , Toast.LENGTH_SHORT).show()
                            }
                        }


                    Toast.makeText(this, "좋아요를 취소했습니다.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
                }

        }

        binding.notliked.setOnClickListener {
            binding.notliked.visibility = View.INVISIBLE
            binding.liked.visibility = View.VISIBLE
            //여기서 부터 시작
            db.collection("Contacts")
                .document(holder_doc.toString())
                .update("liked", FieldValue.increment(1))
                .addOnSuccessListener { result ->

                    //


                    db.collection("Contacts")
                        .document(holder_doc.toString())
                        .get()
                        .addOnSuccessListener { result ->
                            try {
                                with(result) {

                                    binding.likes.text = "${getLong("liked")}"
                                    editor.putBoolean(holder_doc.toString(), true)
                                    editor.commit()

                                }
                            } catch (e: Exception) {
                                Toast.makeText(this, e.toString() , Toast.LENGTH_SHORT).show()
                            }
                        }


                    //


                    Toast.makeText(this, "좋아요를 눌렀습니다.", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
                }
        }


        binding.contentDelete.setOnClickListener {

            val builder = AlertDialog.Builder(this)


            val tvName = TextView(this)
            tvName.text = "\n비밀번호 입력\n"

            val password_edit = EditText(this)
            password_edit.isSingleLine = true

            val mLayout = LinearLayout(this)
            mLayout.orientation = LinearLayout.VERTICAL
            mLayout.setPadding(16)

            mLayout.addView(tvName)
            mLayout.addView(password_edit)

            builder.setView(mLayout)

            builder.setTitle("게시물 삭제")
            builder.setPositiveButton("삭제") { dialog, which ->
                // EditText에서 문자열을 가져와 hashMap으로 만듦

                if(password_edit.text.toString().equals(password.toString())) {
                    db.collection("Contacts")
                        .document(holder_doc.toString())
                        .delete()
                        .addOnSuccessListener {
                            // 성공할 경우
                            Toast.makeText(this, "성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show()

                            finish()
                            //go_board2.putExtra("board_doc", it.toString())
                            // startActivity(go_board2)
                        }
                        .addOnFailureListener { exception ->
                            // 실패할 경우

                            Log.w("MainActivity", "Error getting documents: $exception")
                        }
                } else {
                    Toast.makeText(this, "비밀번호가 일치하지않습니다.", Toast.LENGTH_SHORT).show()
                }



            }
            builder.setNegativeButton("취소") { dialog, which ->

            }
            builder.show()

        }

    }

    fun update() {

        val content_doc = intent.getStringExtra("board_doc")

        db.collection("Contacts")
            .document(content_doc.toString())
            .collection("Comment")// 작업할 컬렉션
            .orderBy("Date", Query.Direction.ASCENDING)
            .get() // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                itemList2.clear()
                for (document in result) {  // 가져온 문서들은 result에 들어감
                    val item2 =
                        comment_Data(
                            document["Comment"] as String,
                            document["Date"] as String,
                            document["Doc"] as String,
                            document["comment_password"] as String,
                            document["content_doc"] as String,
                            document["Content_nickname"] as String,
                            document["Comment_liked"] as Long)
                    itemList2.add(item2)
                }
                adapter.notifyDataSetChanged()// 리사이클러 뷰 갱신
                 binding.commentEdit.setText("")
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.w("TAG", "Error getting documents: $exception")
            }
    }

}