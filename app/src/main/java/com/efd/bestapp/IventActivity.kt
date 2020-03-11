package com.efd.bestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class IventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ivent)

        val text = intent.extras?.get("ivent").toString()
        val part = intent.extras?.get("showpart") as Boolean
        val participate = findViewById<Button>(R.id.participate)
        val desc = findViewById<TextView>(R.id.desc)
        val date = findViewById<TextView>(R.id.date)
        val leave = findViewById<TextView>(R.id.leave)

        var ivents = ArrayList<String>()

        val db = Firebase.firestore
        db.collection("Ivents").document(text)
            .get()
            .addOnSuccessListener {
                desc.text = it.get("desc").toString()
                date.text = it.get("date").toString()
                ivents = it.get("users") as ArrayList<String>
            }
            .addOnFailureListener{
                Log.e("fuck", it.toString())
            }

            if (text.length > 10){
                findViewById<TextView>(R.id.ivent).text = text.substring(0, 10)
            }
            else{
                findViewById<TextView>(R.id.ivent).text = text
            }

        if (!part){
            participate.visibility = View.INVISIBLE
            leave.setOnClickListener {

                val r = ivents.remove(FirebaseAuth.getInstance().currentUser?.email.toString())
                Log.e("fuck", r.toString())

                db.collection("Ivents").document(text)
                    .update("users", ivents)
                    .addOnFailureListener {
                        Log.e("fuck", it.toString())
                    }
                    .addOnCompleteListener {
                        Log.w("fuck", "nice")
                        Toast.makeText(this, "You left", Toast.LENGTH_SHORT).show()
                        this.onBackPressed()
                    }
            }
        }
        else{
            leave.visibility = View.INVISIBLE
            participate.setOnClickListener{
                ivents.add(FirebaseAuth.getInstance().currentUser?.email.toString())

                db.collection("Ivents").document(text)
                    .update("users", ivents)
                    .addOnFailureListener {
                        Log.e("fuck", it.toString())
                    }
                    .addOnCompleteListener {
                        Log.w("fuck", "nice")
                        Toast.makeText(this, "You joined", Toast.LENGTH_SHORT).show()
                        this.onBackPressed()

                    }
            }
        }

        findViewById<ImageView>(R.id.back).setOnClickListener{
            this.onBackPressed()
            this.finish()
        }



        findViewById<Button>(R.id.chat).setOnClickListener {

        }
    }
}
