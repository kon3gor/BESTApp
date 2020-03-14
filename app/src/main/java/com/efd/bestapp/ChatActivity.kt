package com.efd.bestapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class ChatActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    lateinit var eventName: String
    lateinit var listOfMessages: ListView
    lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        eventName = intent.extras?.getString("name").toString()
        val messages = arrayListOf<String>()
        val mesWtime = mutableMapOf<Long, String>()


        listOfMessages = findViewById(R.id.messages_view)
        findViewById<ImageButton>(R.id.imgbtn).setOnClickListener {
            val input = findViewById(R.id.editText) as EditText
            val time = Date().time
            mesWtime.put(time, "${FirebaseAuth.getInstance().currentUser?.email.toString()}<*:*>${input.text}")
            messages.clear()
            mesWtime.toSortedMap().forEach {
                messages.add(it.value)
            }
            val mes = hashMapOf<String, Any>(
                "owner" to FirebaseAuth.getInstance().currentUser?.email.toString(),
                "mes" to input.text.toString(),
                "event" to eventName,
                "time" to time
            )
            db.collection("messages").document()
                .set(mes)
                .addOnSuccessListener {
                    adapter.notifyDataSetChanged()
                }
                .addOnFailureListener {
                    Log.e("fuck", it.toString())
                }
            input.setText("")
        }

        db.collection("messages")
            .get()
            .addOnSuccessListener {
                val documents = it.documents
                documents.forEach {
                    if (eventName == it.getString("event")){
                        mesWtime.put(it.getLong("time")!!.toLong(), "${it.getString("owner")}<*:*>${it.getString("mes")}")
                    }
                }
                mesWtime.toSortedMap().forEach {
                    messages.add(it.value)
                }
                adapter = ChatAdapter(this, messages)
                listOfMessages.adapter = adapter
            }

    }

}