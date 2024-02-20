package com.example.mybook.advice

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.mybook.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class advice_Adapter(val advice_List: ArrayList<String>?, var advice_Text : String) : PagerAdapter() {

    private var imglist = arrayListOf(R.drawable.pager_image1,R.drawable.pager_image3, R.drawable.pager_image2)

    init {
    }

    @SuppressLint("MissingInflatedId")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view = LayoutInflater.from(container.context).inflate(R.layout.pager_layout, container,false)

        var screen_image = view.findViewById<ImageView>(R.id.pager_Image)
        var screen_Advice = view.findViewById<TextView>(R.id.today_Advice)


        if(advice_List!!.size == 3) {
            if(imglist!!.size == 3) {
                screen_image.setImageResource(imglist[position])


                screen_Advice.text = advice_List!![position]


                container.addView(view)
            }
        }
        Log.d("마지막확인", advice_List!!.toString())

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }


    override fun getCount(): Int {
        return imglist.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (view == `object`)
    }
}