package com.example.mybook.reservation

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybook.*
import com.example.mybook.databinding.ActivityReservationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import okhttp3.internal.notifyAll

class Reservation_Activity : AppCompatActivity(), OnItemClick, book_Adapter.OnItemClick{

    private lateinit var binding : ActivityReservationBinding

    private lateinit var book_ViewModelVar : book_ViewModel

    private var SEARCH_DELAY = 300 // milliseconds

    // 핸들러를 선언하여 검색어 입력 딜레이를 관리
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationBinding.inflate(layoutInflater)



        val repository = book_Repository(this.application)

        val viewModelFactory = book_Factory(repository)
        book_ViewModelVar = ViewModelProvider(this, viewModelFactory)[book_ViewModel::class.java]

       book_ViewModelVar.getAll().observe(this@Reservation_Activity) {
           val mAdapter = book_Adapter(this, it, this)
           binding.recyclerViewBook.adapter = mAdapter
           binding.recyclerViewBook.layoutManager = LinearLayoutManager(this)

            mAdapter.setList(it)
            Log.d("첫번째 확인",it.toString())
            mAdapter.notifyDataSetChanged()
        }



            binding.bookSearch.isSubmitButtonEnabled = true
            binding.bookSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {


                    book_ViewModelVar.searchDatabase(newText.orEmpty()).observe(this@Reservation_Activity) { search_it ->
                        // 여기서 현재의 리스트에 새로운 데이터를 추가하거나 업데이트
                        val mAdapter = book_Adapter(this@Reservation_Activity, search_it, this@Reservation_Activity)
                        binding.recyclerViewBook.adapter = mAdapter
                        binding.recyclerViewBook.layoutManager = LinearLayoutManager(this@Reservation_Activity)

                        mAdapter.setList(search_it)
                        Log.d("첫번째 확인",search_it.toString())
                        mAdapter.notifyDataSetChanged()
                    }



                    return true
                }
            })



        setContentView(binding.root)
    }

    override fun deleteTodo(book: Book_Model) {
        book_ViewModelVar.delete(book)
    }

    override fun check_memo(content: String, book_Edit: String, dialog: Dialog) {
        TODO("Not yet implemented")
    }

    override fun close_Dialog(dialog: Dialog) {
        TODO("Not yet implemented")
    }


}