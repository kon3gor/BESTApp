package com.efd.bestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<ImageView>(R.id.acc).setOnClickListener{
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }


    }
}
