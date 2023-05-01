package com.example.firebase_app.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase_app.R
import com.example.firebase_app.model.Product

class itemAdapter(private val itemList: ArrayList<Product>) :
    RecyclerView.Adapter<itemAdapter.itemHolder>() {
    class itemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: EditText
        val itemPrice: EditText
        val itemType: EditText
        val itemImg: ImageView

            init {
                itemName = itemView.findViewById(R.id.nameID)
                itemPrice = itemView.findViewById(R.id.priceID)
                itemType = itemView.findViewById(R.id.typeID)
                itemImg = itemView.findViewById(R.id.itemImgView)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return itemHolder(itemView)
    }

    override fun onBindViewHolder(holder: itemHolder, position: Int) {
        val currentItem = itemList[position]
        holder.itemName.setText(currentItem.nameProduct.toString())
        holder.itemPrice.setText(currentItem.price.toString())
        holder.itemType.setText(currentItem.typeProduct.toString())

        val bytes = android.util.Base64.decode(currentItem.image, android.util.Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        holder.itemImg.setImageBitmap(bitmap)


    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}