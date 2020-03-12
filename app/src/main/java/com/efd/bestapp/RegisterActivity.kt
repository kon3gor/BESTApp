package com.efd.bestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(findViewById(R.id.toolbar))

        auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        val email = findViewById<EditText>(R.id.mail)
        val password = findViewById<EditText>(R.id.password)


        findViewById<Button>(R.id.signup).setOnClickListener{

            auth.createUserWithEmailAndPassword(email.text.toString().trim(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("fuck", "createUserWithEmail:success")

                        val user = hashMapOf(
                            "name" to findViewById<EditText>(R.id.name).text.toString()
                        )
                        db.collection("users").document(email.text.toString())
                            .set(user)
                            .addOnSuccessListener {
                                Log.d("fuck", "success")
                            }
                            .addOnFailureListener {
                                Log.e("fuck", it.toString())
                            }

                        val intent = Intent(this, IventsActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        this.finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("fuck", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }


        }

        findViewById<TextView>(R.id.back).setOnClickListener{
            val intent = Intent(this, LoginSignupActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this.finish()
        }

    }
}
