package com.efd.bestapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


class NewIventActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_ivent)

        val auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        val name = findViewById<EditText>(R.id.name)
        val desc = findViewById<EditText>(R.id.desc)
        val date = findViewById<EditText>(R.id.date)
        val owner = auth.currentUser?.email
        val users = FieldValue.arrayUnion(owner)
        var time: Long = 0
        val curTime = Date().time


        findViewById<Button>(R.id.submit).setOnClickListener{
            if (name.text.toString() == "" || desc.text.toString() == "" || date.text.toString() == ""){
                Toast.makeText(this, "Fill all fields !!", Toast.LENGTH_SHORT).show()
            }
            else if (time <= curTime){
                Toast.makeText(this, "Wrong date !!", Toast.LENGTH_SHORT).show()
            }
            else{
                val ivent = hashMapOf(
                    "name" to name.text.toString(),
                    "desc" to desc.text.toString(),
                    "owner" to owner,
                    "time" to time,
                    "date" to date.text.toString(),
                    "users" to users
                )
                db.collection("Ivents").document(name.text.toString())
                    .set(ivent)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
                        this.onBackPressed()
                        this.finish()
                    }
                    .addOnFailureListener {
                        Log.e("fuck", it.toString())
                    }
            }
        }

        findViewById<Button>(R.id.choosedate).setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            var year_returned = 0
            var month_returned = 0
            var day_returned = 0

            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minuteR ->
                c.set(year_returned, month_returned, day_returned, hourOfDay, minuteR, 0)
                time = c.timeInMillis
                Log.e("fuck", time.toString())
                Log.e("fuck", curTime.toString())

            }, hour, minute, true)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, yearR, monthR, dayR->
                year_returned = yearR
                month_returned = monthR
                day_returned = dayR
                tpd.show()
            }, year, month, day)

            dpd.show()
        }

        findViewById<ImageView>(R.id.back).setOnClickListener {
            this.onBackPressed()
            this.finish()
        }
    }
}
