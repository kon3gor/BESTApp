package com.efd.bestapp.ui.home

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
import androidx.recyclerview.widget.RecyclerView
import com.efd.bestapp.IventAdapter
import com.efd.bestapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.reflect.typeOf

class HomeFragment : Fragment() {

    private lateinit var listView: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        listView = root.findViewById(R.id.list)
        getIvents()
        return root
    }

    override fun onStart() {
        getIvents()
        super.onStart()
    }

    private fun getIvents(): ArrayList<String> {
        val db = Firebase.firestore
        val auth = FirebaseAuth.getInstance()
        val ivents = ArrayList<String>()
        var map = mutableMapOf<Long, String>()
        db.collection("Ivents")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val list = document.get("users") as ArrayList<String>
                    if (list.contains(auth.currentUser?.email)){
                        Log.e("fuck", "nice")
                        val name = document.get("name").toString()
                        val time = document.get("time").toString().toLong()
                        map.put(time, name)
                    }
                }
                map.toSortedMap().values.forEach {
                    ivents.add(it)
                }
                listView.apply {
                    adapter = IventAdapter(context, ivents, false)
                }

            }
            .addOnFailureListener { exception ->
                Log.w("fuck", "Error getting documents.", exception)
            }
        return ivents
    }
}