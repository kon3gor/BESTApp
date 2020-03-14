package com.efd.bestapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(context: Context, data: ArrayList<String>): BaseAdapter() {

    val messages = data
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val cont: Context = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val my = inflater.inflate(R.layout.my_message, parent, false)
        val their = inflater.inflate(R.layout.their_message, parent, false)
        val email = messages[position].split("<*:*>")[0]
        val mes = messages[position].split("<*:*>")[1]

        if (email == FirebaseAuth.getInstance().currentUser?.email){
            val message = my.findViewById<TextView>(R.id.message_body)
            message.text = mes
            return my
        }
        else{
            val messsage = their.findViewById<TextView>(R.id.message_body)
            val author = their.findViewById<TextView>(R.id.author)
            messsage.text = mes
            author.text = email
            return their
        }
    }

    override fun getItem(position: Int): String {
        return messages[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return messages.size
    }


}