package com.example.mybook

import android.app.Dialog

interface OnItemClick {

    fun deleteTodo(cloths: Book_Model)

    fun check_memo(content : String,book_Edit : String ,dialog : Dialog)

    fun close_Dialog(dialog : Dialog)

}