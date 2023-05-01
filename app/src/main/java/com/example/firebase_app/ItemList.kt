package com.example.firebase_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase_app.adapter.itemAdapter
import com.example.firebase_app.databinding.ActivityItemListBinding
import com.example.firebase_app.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class ItemList : AppCompatActivity() {
    private lateinit var binding: ActivityItemListBinding
    private lateinit var data: DatabaseReference
    private lateinit var itemArray: ArrayList<Product>
    private lateinit var itemRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemRecyclerView = findViewById(R.id.listItem)
        itemRecyclerView.layoutManager = LinearLayoutManager(this)
        itemRecyclerView.hasFixedSize()
        itemArray = arrayListOf<Product>()
        getData()

    }

    private fun getData() {
        data = FirebaseDatabase.getInstance().getReference("products")
        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (productsnapshot in snapshot.children){
                        val product = productsnapshot.getValue(Product::class.java)
                        itemArray.add(product!!)
                    }
                    itemRecyclerView.adapter = itemAdapter(itemArray)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}