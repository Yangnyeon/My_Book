package com.example.mybook.community

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.example.mybook.R
import com.example.mybook.databinding.BookItemBinding
import com.example.mybook.databinding.ListLayoutBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ListAdapter (val itemList: ArrayList<community_Data>,val context: Context): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)

        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = itemList[position].name
        holder.content.text = itemList[position].number
        holder.community_date.text = itemList[position].com_date
        holder.nickname.text = itemList[position].Nickname
        holder.like_count.text = itemList[position].liked.toString()
        holder.eye_count.text = itemList[position].eye.toString()
        var photo= itemList[position]
        holder.bind(photo)



        val db = FirebaseFirestore.getInstance()

        db.collection("Contacts")
            .document(itemList[position].doc)
            .collection("Comment")// 작업할 컬렉션
            .orderBy("Date", Query.Direction.ASCENDING)
            .get() // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                holder.comment_count.text = result.size().toString()
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.w("TAG", "Error getting documents: $exception")
            }



        holder.itemView.setOnClickListener {

            var title = itemList[position].name
            var date = itemList[position].number
            var content = itemList[position].com_date
            var doc = itemList[position].doc
            var liked = itemList[position].liked.toString()
            var nickname = itemList[position].Nickname
            var password = itemList[position].password


            val go_board = Intent(context, community_Holder::class.java)
            go_board.putExtra("board_title", title)
            go_board.putExtra("board_date", date)
            go_board.putExtra("board_content", content)
            go_board.putExtra("board_doc", doc)
            go_board.putExtra("board_liked", liked)
            go_board.putExtra("board_Nickname", nickname)
            go_board.putExtra("board_password", password)



            db.collection("Contacts")
                .document(doc)
                .update("eye_count", FieldValue.increment(+1))
                .addOnSuccessListener { result ->
                }
                .addOnFailureListener { exception ->
                }

            context.startActivity(go_board)
        }

    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.list_tv_name)
        val content: TextView = itemView.findViewById(R.id.list_tv_content)
        val community_date: TextView = itemView.findViewById(R.id.list_tv_date)
        val comment_count = itemView.findViewById<TextView>(R.id.comment_count)
        val like_count = itemView.findViewById<TextView>(R.id.thumb_count)
        val nickname = itemView.findViewById<TextView>(R.id.list_tv_nickname)
        val eye_count = itemView.findViewById<TextView>(R.id.eye_count)

        var imageIv: ImageView = itemView.findViewById(R.id.list_image)

        fun bind(listlayout: community_Data) {
            Glide.with(context).load(listlayout.imageUrl)
                .fallback(R.drawable.ic_baseline_add_a_photo_24)
                .error(R.drawable.ic_baseline_add_a_photo_24)
                .into(imageIv)


            imageIv.load(listlayout.imageUrl) {
                transformations(CircleCropTransformation())
                placeholder(null)
                error(R.drawable.ic_baseline_add_a_photo_24)
            }

        }
    }

}