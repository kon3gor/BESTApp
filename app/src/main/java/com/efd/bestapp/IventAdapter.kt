package com.efd.bestapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import kotlin.time.measureTime


class IventAdapter(context: Context, private val ivents: ArrayList<String>, public val flag: Boolean) : BaseAdapter(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val cont: Context = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item, parent, false)

        val text = rowView.findViewById<TextView>(R.id.title)
        val img = rowView.findViewById<ImageView>(R.id.img)

        text.text = ivents[position]

        rowView.setOnClickListener{
            val intent = Intent(cont, IventActivity::class.java).apply {
                putExtra("ivent", ivents[position])
                if (flag){
                    putExtra("showpart", true)
                }
                else{
                    putExtra("showpart", false)
                }
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            cont.startActivity(intent)
        }

        return rowView

    }

    override fun getItem(position: Int): Any {
        return ivents[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return ivents.size
    }

}