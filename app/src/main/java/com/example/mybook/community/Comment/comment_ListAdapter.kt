package com.example.mybook.community.Comment

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.example.mybook.R
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class comment_ListAdapter(val itemList: ArrayList<comment_Data>, val context: Context): RecyclerView.Adapter<comment_ListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): comment_ListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_comment_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.Comment.text = itemList[position].Comment
        holder.date.text = itemList[position].Date
        holder.Comment_nickname.text = itemList[position].Content_nickname
        holder.Comment_thumbs_count.text = itemList[position].Comment_liked.toString()

        var settings: SharedPreferences = context.getSharedPreferences(
            "Comment_like_tmp",
            Context.MODE_PRIVATE
        )

        var editor: SharedPreferences.Editor = settings.edit()

        val db = FirebaseFirestore.getInstance()

        if (settings.getBoolean(itemList[position].Doc.toString(), false)) {
            holder.Comment_thumbsnotliked.visibility = View.INVISIBLE
            holder.Comment_thumbsliked.visibility = View.VISIBLE
        } else {
            holder.Comment_thumbsnotliked.visibility = View.VISIBLE
            holder.Comment_thumbsliked.visibility = View.INVISIBLE
        }

        holder.Comment_thumbsliked.setOnClickListener {
            holder.Comment_thumbsnotliked.visibility = View.VISIBLE
            holder.Comment_thumbsliked.visibility = View.INVISIBLE

            db.collection("Contacts")
                .document(itemList[position].content_doc.toString())
                .collection("Comment")
                .document(itemList[position].Doc.toString())
                .update("Comment_liked", FieldValue.increment(-1))
                .addOnSuccessListener { result ->

                    db.collection("Contacts")
                        .document(itemList[position].content_doc.toString())
                        .collection("Comment")
                        .document(itemList[position].Doc.toString())
                        .get()
                        .addOnSuccessListener { result ->
                            try {
                                with(result) {
                                    holder.Comment_thumbs_count.text = "${getLong("Comment_liked")}"
                                    editor.remove(itemList[position].Doc)
                                    editor.commit()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    Toast.makeText(context, "좋아요를 취소했습니다.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show()
                }

        }

        holder.Comment_thumbsnotliked.setOnClickListener {
            holder.Comment_thumbsnotliked.visibility = View.INVISIBLE
            holder.Comment_thumbsliked.visibility = View.VISIBLE

            db.collection("Contacts")
                .document(itemList[position].content_doc.toString())
                .collection("Comment")
                .document(itemList[position].Doc.toString())
                .update("Comment_liked", FieldValue.increment(1))
                .addOnSuccessListener { result ->

                    db.collection("Contacts")
                        .document(itemList[position].content_doc.toString())
                        .collection("Comment")
                        .document(itemList[position].Doc.toString())
                        .get()
                        .addOnSuccessListener { result ->
                            try {
                                with(result) {
                                    holder.Comment_thumbs_count.text =
                                        "${getLong("Comment_liked")}"
                                    editor.putBoolean(itemList[position].Doc.toString(), true)
                                    editor.commit()

                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    Toast.makeText(context, "좋아요를 눌렀습니다.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show()
                }

        }

        holder.comment_delete.setOnClickListener {

            val db = FirebaseFirestore.getInstance()

            val comment_delete_builder = AlertDialog.Builder(context)


            // 대화상자에 텍스트 입력 필드 추가, 대충 만들었음
            val tvName = TextView(context)
            tvName.text = "\n비밀번호 입력\n"

            val password_edit = EditText(context)
            password_edit.isSingleLine = true

            val mLayout = LinearLayout(context)
            mLayout.orientation = LinearLayout.VERTICAL
            mLayout.setPadding(16)
            mLayout.addView(tvName)
            mLayout.addView(password_edit)

            comment_delete_builder.setView(mLayout)

            comment_delete_builder.setTitle("댓글 삭제")
            comment_delete_builder.setPositiveButton("삭제") { dialog, which ->
                // EditText에서 문자열을 가져와 hashMap으로 만듦

                if (password_edit.text.toString().equals(itemList[position].comment_password)) {
                    db.collection("Contacts")
                        .document(itemList[position].content_doc.toString())
                        .collection("Comment")
                        .document(itemList[position].Doc.toString())
                        .delete()
                        .addOnSuccessListener {
                            // 성공할 경우
                            Toast.makeText(context, "성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show()

                            //go_board2.putExtra("board_doc", it.toString())
                            // startActivity(go_board2)
                        }
                        .addOnFailureListener { exception ->
                            // 실패할 경우
                            Toast.makeText(context, "오류", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(context, "비밀번호가 일치하지않습니다.", Toast.LENGTH_SHORT).show()
                }

            }
            comment_delete_builder.setNegativeButton("취소") { dialog, which ->

            }
            comment_delete_builder.show()

        }

        //holder.Comment_thumbsliked.setOnClickListener {

        //}

    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Comment: TextView = itemView.findViewById(R.id.comment123)
        val date: TextView = itemView.findViewById(R.id.comment_date123)
        val comment_delete: ImageView = itemView.findViewById(R.id.comment_delete)
        val Comment_nickname: TextView = itemView.findViewById(R.id.comment_nickname)
        val Comment_thumbsliked: ImageView = itemView.findViewById(R.id.thumb_liked)
        val Comment_thumbsnotliked: ImageView = itemView.findViewById(R.id.thumb_notliked)
        val Comment_thumbs_count: TextView = itemView.findViewById(R.id.Comment_thumb_count)
    }
}