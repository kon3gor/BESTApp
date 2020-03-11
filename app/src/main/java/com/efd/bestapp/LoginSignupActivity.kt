package com.efd.bestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginSignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.login -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                this.finish()
            }
            R.id.signup -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                this.finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup)
        setSupportActionBar(findViewById(R.id.toolbar))

        auth = FirebaseAuth.getInstance()

        findViewById<Button>(R.id.login).setOnClickListener(clickListener)
        findViewById<Button>(R.id.signup).setOnClickListener(clickListener)

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null){
            val intent = Intent(this, IventsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this.finish()
        }
    }
}
