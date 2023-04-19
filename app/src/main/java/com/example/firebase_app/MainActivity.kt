package com.example.firebase_app

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.firebase_app.databinding.ActivityMainBinding
import com.example.firebase_app.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var data: DatabaseReference

    var productImg: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        firebaseAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()



        setContentView(binding.root)
    }

    fun insert_data(view: View) {
        val productName = binding.pdName.text.toString()
        val productType = binding.pdType.text.toString()
        val productPrice = binding.pdPrice.text.toString()

        data = FirebaseDatabase.getInstance().getReference("products")
        val product = Product(productName, productType, productPrice, productImg)
        val databaseReference = FirebaseDatabase.getInstance().reference
        val id = databaseReference.push().key

        data.child(id.toString()).setValue(product).addOnSuccessListener {
            binding.pdName.text.clear()
            binding.pdType.text.clear()
            productImg = ""
            Toast.makeText(this, "Adding successful", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Adding has been failed", Toast.LENGTH_SHORT).show()
        }

    }

    fun insert_img(view: View) {
        val myfileintent = Intent(Intent.ACTION_GET_CONTENT)
        myfileintent.type = "image/*"
        ActivityResultLauncher.launch(myfileintent)
    }

    private val ActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val uri = result.data!!.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val myBitmap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val bytes = stream.toByteArray()

                productImg = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT)
                binding.imageView.setImageBitmap(myBitmap)

                Toast.makeText(this,"Image Selected", Toast.LENGTH_SHORT).show()

            } catch (ex: Exception) {
                Toast.makeText(this,ex.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }


}