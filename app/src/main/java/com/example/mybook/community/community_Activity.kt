package com.example.mybook.community

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybook.R
import com.example.mybook.databinding.ActivityCommunityBinding
import com.example.mybook.databinding.ActivityMainBinding
import com.example.mybook.loading_Screen
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class community_Activity : AppCompatActivity() {

    private var mBinding : ActivityCommunityBinding?= null
    private val binding get() = mBinding!!

    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언

    val itemList = arrayListOf<community_Data>()    // 리스트 아이템 배열

    val Question_itemList = arrayListOf<community_Data>()    // 리스트 아이템 배열

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCommunityBinding.inflate(layoutInflater)

        val adapter123 = ListAdapter(itemList, this@community_Activity)   // 리사이클러 뷰 어댑터

        val loadingAnimDialog = loading_Screen(this@community_Activity)

        loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        loadingAnimDialog.show()

        binding.rvList.layoutManager = LinearLayoutManager(this@community_Activity, LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = adapter123

        CoroutineScope(Dispatchers.Main).launch {
            db.collection("Contacts") // 작업할 컬렉션
                //.limit(3)
                .orderBy("com_date", Query.Direction.DESCENDING)
                .get() // 문서 가져오기
                .addOnSuccessListener { result ->
                    // 성공할 경우
                    loadingAnimDialog.dismiss()

                    itemList.clear()
                    for (document in result) {  // 가져온 문서들은 result에 들어감
                        val item =
                            community_Data(
                                document["name"] as String,
                                document["number"] as String,
                                document["com_date"] as String,
                                document["password"] as String,
                                document["doc"] as String,
                                document["nickname"] as String,
                                document["liked"] as Long,
                                document["eye_count"] as Long,
                                document["imageUrl"] as String
                            )
                        itemList.add(item)
                    }
                    adapter123.notifyDataSetChanged()// 리사이클러 뷰 갱신
                }
                .addOnFailureListener { exception ->
                    // 실패할 경우z
                    Log.w("MainActivity", "Error getting documents: $exception")
                }

        }

        binding.btnWrite.setOnClickListener {
            startActivity(Intent(this@community_Activity, community_Write::class.java))
        }


        binding.CommunitySearchview.setOnQueryTextListener(searchViewTextListener)

        setContentView(binding.root)
    }

    private var searchViewTextListener: androidx.appcompat.widget.SearchView.OnQueryTextListener =
        object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
            override fun onQueryTextSubmit(s: String): Boolean {



                return true
            }


            //텍스트 입력/수정시에 호출
            override fun onQueryTextChange(s: String): Boolean {

                CoroutineScope(Dispatchers.Main).launch {
                    if(s != null) {

                        val adapter123 = ListAdapter(itemList, this@community_Activity)

                        binding.rvList.layoutManager =
                            LinearLayoutManager(this@community_Activity, LinearLayoutManager.VERTICAL, false)
                        binding.rvList.adapter = adapter123

                        db.collection("Contacts") // 작업할 컬렉션
                            .orderBy("com_date", Query.Direction.DESCENDING)
                            .get() // 문서 가져오기
                            .addOnSuccessListener { result ->
                                itemList.clear()
                                for (document in result) {  // 가져온 문서들은 result에 들어감
                                    if (document.getString("name").toString()!!.contains(s)) {
                                        var item123 =
                                            community_Data(
                                                document["name"] as String,
                                                document["number"] as String,
                                                document["com_date"] as String,
                                                document["password"] as String,
                                                document["doc"] as String,
                                                document["nickname"] as String,
                                                document["liked"] as Long,
                                                document["eye_count"] as Long,
                                                document["imageUrl"] as String
                                            )
                                        itemList.add(item123)
                                    }
                                }
                                adapter123.notifyDataSetChanged()// 리사이클러 뷰 갱신
                            }
                            .addOnFailureListener { exception ->
                                // 실패할 경우z
                                Log.w("MainActivity", "Error getting documents: $exception")
                            }
                    } else if(s == "") {
                        onResume()
                    }
                }

                return true
            }
        }
}