package com.example.mybook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_Table")
class Book_Model (
    var content : String,
    var book_Edit : String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}