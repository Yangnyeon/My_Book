package com.example.mybook

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.mybook.databinding.BookItemBinding
import java.util.ArrayList

class book_Adapter(listener: OnItemClick,val itemList: List<Book_Model>, var get_context : Context) : RecyclerView.Adapter<book_Adapter.TodoViewHolder>() {


    private val mCallback = listener

    private val items = ArrayList<Book_Model>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : TodoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = BookItemBinding.inflate(layoutInflater)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(items[position])
        // holder.bind123(itemList[position])

        holder.itemView.setOnClickListener {

        }
    }


    inner class TodoViewHolder(private val binding: BookItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(cloths : Book_Model) {

            binding.book = cloths

            binding.delete.setOnClickListener {

                val builder = AlertDialog.Builder(get_context)
                builder.setTitle("삭제하시겟습니까?")
                    .setPositiveButton("확인") { dialog, _ ->
                        mCallback.deleteTodo(cloths)
                        dialog.dismiss() // 다이얼로그를 닫음
                    }
                    .setNegativeButton("취소") { dialog: DialogInterface, _: Int ->
                      dialog.dismiss()
                    }
                val dialog = builder.create()
                dialog.show()
            }


        }
    }

    /*
    fun setList(English: List<English>) {
        items.clear()
        items.addAll(English)
    }

     */
    fun setList(englishes: List<Book_Model>) {
        items.clear()
        items.addAll(englishes)
    }


    interface OnItemClick {

        fun deleteTodo(cloths: Book_Model)

    }



}