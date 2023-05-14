package com.example.e_commercek.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercek.R
import com.example.e_commercek.data.DataModel
import com.example.e_commercek.data.MyAdapter
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class HomeScreenActivity : AppCompatActivity() {

    private var recyclerViewActivity: RecyclerView? = null


    private var drawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    private var searchViewHS: SearchView? = null
    private var homeScreenHamburgerIV: ImageView? = null
    companion object {
        @SuppressLint("StaticFieldLeak")
        var activity: Activity? = null
    }
    var db: FirebaseFirestore? = null


    var itemList: ArrayList<DataModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        init()


        itemList = ArrayList()
        itemList!!.add(DataModel(R.drawable.shoe_image, "Campus Shoe", "Get your shoe", 400.00))
        itemList!!.add(DataModel(R.drawable.shoe_image, "Campus Shoe", "Get your shoe", 400.00))
        itemList!!.add(DataModel(R.drawable.shoppingcart, "Shopping Cart", "Get your cart", 700.00))
        itemList!!.add(DataModel(R.drawable.shoe_image, "Campus Shoe", "Get your shoe", 400.00))
        itemList!!.add(DataModel(R.drawable.shoe_image, "Campus Shoe", "Get your shoe", 400.00))
        itemList!!.add(DataModel(R.drawable.shoe_image, "Campus Shoe", "Get your shoe", 400.00))
        itemList!!.add(DataModel(R.drawable.shoe_image, "Campus Shoe", "Get your shoe", 400.00))
        itemList!!.add(DataModel(R.drawable.shoe_image, "Campus Shoe", "Get your shoe", 400.00))
        itemList!!.add(
            DataModel(
                Uri.parse("https://www.bigbasket.com/media/uploads/p/l/20006339_1-maple-stainless-steel-e-kettle.jpg"),
                "Kettle", "Stainless steel 1L", 1000.00
            )
        )
        itemList!!.add(
            DataModel(
                Uri.parse("https://img.etimg.com/thumb/width-640,height-480,imgsize-66442,resizemode-1,msid-95250524/top-trending-products/electronics/tv-appliances/best-tv-in-india.jpg"),
                "Sony H1L", "Best TV in India - Best LED, 4K HDR TVs In India", 75000.00
            )
        )
        insertFromFireStore()

        val myAdapter = MyAdapter(this, itemList!!)

        recyclerViewActivity!!.adapter = myAdapter
        recyclerViewActivity!!.setHasFixedSize(true)
        recyclerViewActivity!!.layoutManager = GridLayoutManager(this, 2)

        val navigationHeader = navigationView!!.getHeaderView(0)
        val navHeaderName = navigationHeader.findViewById<TextView>(R.id.navHeaderName)
        val navHeaderEmail = navigationHeader.findViewById<TextView>(R.id.navHeaderEmail)

        navHeaderName.text =
            getSharedPreferences("CREDENTIALS", MODE_PRIVATE).getString("NAME", "Arijit Modak")
        navHeaderEmail.text = getSharedPreferences("CREDENTIALS", MODE_PRIVATE).getString("EMAIL", "arijitmodak2003@gmail.com")

        val circleImageView2 = navigationHeader.findViewById<CircleImageView>(R.id.circleImageView2)
        circleImageView2.setOnClickListener {
            val intent = Intent(this@HomeScreenActivity, ProfileActivity::class.java)
            startActivity(intent)
        }

        navigationView!!.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.shoppingCartMenu -> {
                    startActivity(Intent(this@HomeScreenActivity, ShoppingCartActivity::class.java))
                }
            }
            true
        }

        searchViewHS!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val filteredItemList = ArrayList<DataModel>()
                for (i in itemList!!.indices) {
                    if (itemList!![i].getItemName()!!.lowercase()
                            .contains(newText.lowercase())
                    ) {
                        filteredItemList.add(itemList!![i])
                    }
                }
                val filteredMyAdapter = MyAdapter(this@HomeScreenActivity, filteredItemList)
                recyclerViewActivity!!.adapter = filteredMyAdapter
                recyclerViewActivity!!.setHasFixedSize(true)
                recyclerViewActivity!!.layoutManager = GridLayoutManager(this@HomeScreenActivity, 2)
                return true
            }
        })



    }


    private fun init() {
        recyclerViewActivity = findViewById(R.id.recyclerViewActivity)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        searchViewHS = findViewById(R.id.searchViewHS)
        homeScreenHamburgerIV = findViewById(R.id.homeScreenHamburgerIV)
        activity = this@HomeScreenActivity
        db = FirebaseFirestore.getInstance()
    }

    private fun insertFromFireStore() {
        db!!.collection("products").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                //itemList.removeAll(itemList);
                val list = queryDocumentSnapshots.documents
                for (d in list) {
                    val map = d.data!!
                    for ((_, value) in map) {
                        //for(Map<String,Object> mapEE:mapEntry.get){}
                        if (value is Map<*, *>) {
                            val nestedMap =
                                value as Map<String, Any>
                            var NAME = ""
                            var IMAGE = ""
                            var DESCRIPTION = ""
                            var PRICE:Double = 0.0
                            //String id=d.getId();
                            for ((key, value1) in nestedMap) {
                                if (key == "NAME") {
                                    NAME = value1.toString()
                                    Log.d("XYZXX", value1.toString())
                                } else if (key == "IMAGE") {
                                    IMAGE = value1.toString()
                                } else if (key == "PRICE") {
                                    PRICE = value1.toString().toDouble()
                                } else if (key == "DESCRIPTION") {
                                    DESCRIPTION = value1.toString()
                                }
                            }
                            itemList!!.add(
                                DataModel(
                                    Uri.parse(IMAGE),
                                    NAME,
                                    DESCRIPTION,
                                    PRICE
                                )
                            )
                        }
                    }
                }
                Log.d("my_data", itemList.toString())
                //startActivity(new Intent(MainActivity.this, QRScanner.class));
            }
            .addOnFailureListener {
                Toast.makeText(
                    this@HomeScreenActivity,
                    "Not able to load the items",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}