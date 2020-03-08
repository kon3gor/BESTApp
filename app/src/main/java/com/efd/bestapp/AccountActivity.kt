package com.efd.bestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class AccountActivity : AppCompatActivity() {

    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.delete -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Delete account")
                builder.setMessage("Are you sure you want to delete your account ?")

                builder.setPositiveButton("Yes"){dialog, which ->
                    Toast.makeText(applicationContext,"Deleted",Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton("No"){dialog, which -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()

            }
            R.id.chname -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Change name")
                builder.setMessage("Are you sure you want to change your account name ?")

                builder.setPositiveButton("Yes"){dialog, which ->
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

                builder.setPositiveButton("Yes"){dialog, which ->
                    Toast.makeText(applicationContext,"Changed",Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton("No"){dialog, which -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<ImageView>(R.id.back).setOnClickListener{
            this.onBackPressed()
            this.finish()
        }

        findViewById<TextView>(R.id.delete).setOnClickListener(clickListener)
        findViewById<TextView>(R.id.chpass).setOnClickListener(clickListener)
        findViewById<TextView>(R.id.chname).setOnClickListener(clickListener)
    }
}
