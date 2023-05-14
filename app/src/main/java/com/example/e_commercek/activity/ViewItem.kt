package com.example.e_commercek.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.e_commercek.R

class ViewItem : AppCompatActivity() {

    private var backBtn_IVVI: ImageView? = null
    private var itemImageIVVI: ImageView? = null
    private var itemNameTVVI: TextView? = null
    private var itemDescriptionTVVI: TextView? = null
    private var priceTVVI: TextView? = null
    private var noOfItemTVVI: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)

        init()
        backBtn_IVVI!!.setOnClickListener { onBackPressed() }

        val IMAGE2 = intent.getStringExtra("IMAGE2")
        val IMAGE1 = intent.getIntExtra("IMAGE1", 0)
        val ITEM_NAME = intent.getStringExtra("ITEM_NAME")
        val ITEM_DESCRIPTION = intent.getStringExtra("ITEM_DESCRIPTION")
        val ITEM_PRICE = intent.getDoubleExtra("ITEM_PRICE", 400.00)
        val ITEM_QTY = intent.getIntExtra("ITEM_QTY", 1)

        if (IMAGE2 == "") {
            itemImageIVVI!!.setImageResource(IMAGE1)
        } else {
            Glide.with(this@ViewItem).load(IMAGE2).into(itemImageIVVI!!)
        }

        itemNameTVVI!!.text = ITEM_NAME
        itemDescriptionTVVI!!.text = ITEM_DESCRIPTION
        priceTVVI!!.text = "Rs: " + (ITEM_PRICE * ITEM_QTY).toString()
        noOfItemTVVI!!.text = ITEM_QTY.toString()
    }

    private fun init() {
        backBtn_IVVI = findViewById(R.id.backBtn_IVVI)
        itemImageIVVI = findViewById(R.id.itemImageIVVI)
        itemNameTVVI = findViewById(R.id.itemNameTVVI)
        itemDescriptionTVVI = findViewById(R.id.itemDescriptionTVVI)
        priceTVVI = findViewById(R.id.priceTVVI)
        noOfItemTVVI = findViewById(R.id.noOfItemTVVI)
    }
}