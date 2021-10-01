package com.lf.generatemobilephone.adapter

import com.lf.generatemobilephone.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * @ClassName com.lf.generatemobilephone.adapter.NumberAdapter
 * @Description TODO
 * @date 2021/10/1 12:57
 * @Version 1.0
 * @Author 李建新
 */
class NumberAdapter(var context: Context? = null,var number: Array<String>) : BaseAdapter() {

    fun Number2(context: Context?, number: Array<String>) {
        this.context = context
        this.number = number
    }

    override fun getCount(): Int {
        return number.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.number, null)
            val textView = convertView.findViewById<View>(R.id.number) as TextView
            textView.text = number[position]
        }
        return convertView
    }
}