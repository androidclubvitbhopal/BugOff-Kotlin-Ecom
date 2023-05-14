package com.example.e_commercek.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercek.R
import com.example.e_commercek.data.MyAdapter

class ShoppingCartActivity : AppCompatActivity() {


    private var recyclerViewShoppingCart: RecyclerView? = null
    private var totalAmountLL: LinearLayout? = null
    private var noOfItemsInCartTV: TextView? = null
    private var backBtnSCTIV: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        init()
        backBtnSCTIV!!.setOnClickListener { onBackPressed() }
        val myAdapter = MyAdapter(this, MyAdapter.shoppingList, MyAdapter.SHOPPING_CART_ACT)
        if (MyAdapter.shoppingList.size == 0) {
            totalAmountLL!!.visibility = View.INVISIBLE
        }
        val noOfItemsInCart: String = MyAdapter.shoppingList.size.toString() + " items are in the cart"
        noOfItemsInCartTV!!.text = noOfItemsInCart
        recyclerViewShoppingCart!!.adapter = myAdapter
        recyclerViewShoppingCart!!.setHasFixedSize(true)
        recyclerViewShoppingCart!!.layoutManager = LinearLayoutManager(this@ShoppingCartActivity)
    }

    private fun init() {
        recyclerViewShoppingCart = findViewById(R.id.recyclerViewShoppingCart)
        totalAmountLL = findViewById(R.id.totalAmountLL)
        noOfItemsInCartTV = findViewById(R.id.noOfItemsInCartTV)
        backBtnSCTIV = findViewById(R.id.backBtnSCTIV)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}