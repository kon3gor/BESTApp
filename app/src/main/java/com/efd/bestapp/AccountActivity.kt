package com.efd.bestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AccountActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.delete -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Delete account")
                builder.setMessage("Are you sure you want to delete your account ?")

                builder.setPositiveButton("Yes"){_, _ ->
                    auth.currentUser?.delete()
                    auth.signOut()
                    val intent = Intent(this, LoginSignupActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    this.finish()
                    Toast.makeText(applicationContext,"Deleted",Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton("No"){_, _ -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()

            }
            R.id.chname -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Change name")
                builder.setMessage("Are you sure you want to change your account name ?")

                builder.setPositiveButton("Yes"){_, _ ->
                    Toast.makeText(applicationContext,"Changed",Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton("No"){dialog, which -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
            R.id.chpass -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Change password")
                builder.setMessage("Are you sure you want to change your password ?")

                builder.setPositiveButton("Yes"){_, _ ->
                    Toast.makeText(applicationContext,"Changed",Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton("No"){_, _ -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
            R.id.logout -> {
                auth.signOut()

                val intent = Intent(this, LoginSignupActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()
            }
            R.id.fbtn -> {

                val intent = Intent(this, NewIventActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        setSupportActionBar(findViewById(R.id.toolbar))

        auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        findViewById<ImageView>(R.id.back).setOnClickListener{
            this.onBackPressed()
            this.finish()
        }

        db.collection("users").document(auth.currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                findViewById<TextView>(R.id.accname).text = it.get("name").toString()
            }
            .addOnFailureListener {
                Log.e("fuck", it.toString())
            }

        findViewById<TextView>(R.id.accmail).text = auth.currentUser?.email


        findViewById<TextView>(R.id.delete).setOnClickListener(clickListener)
        findViewById<TextView>(R.id.chpass).setOnClickListener(clickListener)
        findViewById<TextView>(R.id.chname).setOnClickListener(clickListener)
        findViewById<TextView>(R.id.logout).setOnClickListener(clickListener)
        findViewById<FloatingActionButton>(R.id.fbtn).setOnClickListener(clickListener)

    }
}
