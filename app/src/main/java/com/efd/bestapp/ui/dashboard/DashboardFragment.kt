package com.efd.bestapp.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.efd.bestapp.IventAdapter
import com.efd.bestapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DashboardFragment : Fragment() {

    private lateinit var listView: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        listView = root.findViewById(R.id.list)
        getIvents()
        return root
    }

    private fun getIvents(): ArrayList<String> {
        val db = Firebase.firestore
        val auth = FirebaseAuth.getInstance()
        val ivents = ArrayList<String>()
        db.collection("Ivents")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    ivents.add(document.get("name").toString())
                }
                listView.apply {
                    adapter = IventAdapter(context, ivents, true)
                }

            }
            .addOnFailureListener { exception ->
                Log.w("fuck", "Error getting documents.", exception)
            }
        return ivents
    }
}