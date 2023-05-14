package com.example.e_commercek.data

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commercek.R
import com.example.e_commercek.activity.ViewItem
import com.google.android.material.card.MaterialCardView
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.String
import kotlin.Int
import kotlin.toString

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    var context: Context
    var itemList: ArrayList<DataModel>
    private var flag: Int
    companion object {
        const val SHOPPING_CART_ACT = 100
        var shoppingList = ArrayList<DataModel>()
    }

    constructor(context: Context, itemList: ArrayList<DataModel>) {
        this.context = context
        this.itemList = itemList
        flag = 0
    }

    constructor(context: Context, itemList: ArrayList<DataModel>, flag: Int) {
        this.context = context
        this.itemList = itemList
        this.flag = flag
    }

    var count = 0

    //here count variable is not creating for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        count = itemList[position].getNoOfItem()
        if (itemList[position].getImage2() != null) {
            Glide.with(context).load(itemList[position].getImage2())
                .into(holder.sellingItemImageOfListItem)
        } else {
            holder.sellingItemImageOfListItem.setImageResource(itemList[position].getImage1())
        }
        holder.sellingItemNameOfListItem.text = itemList[position].getItemName()
        holder.noOfItemTV.text = String.valueOf(itemList[holder.adapterPosition].getNoOfItem())
        //changes
        holder.itemDescriptionTV!!.setText(itemList[position].getItemDescription())
        val price = "Rs: " + String.valueOf(itemList[position].getSingleItemPrice())
        holder.priceTV!!.setText(price)
        holder.decrementItemQty.setOnClickListener {
            if (count > 1) {
                count--
                holder.noOfItemTV.text = count.toString()
                itemList[holder.adapterPosition].setNoOfItem(count)
                val price =
                    "Rs: " + String.valueOf(itemList[holder.adapterPosition].getSingleItemPrice() * count)
                holder.priceTV!!.setText(price)
            } else Toast.makeText(
                context,
                "Sorry item can not be smaller than 1",
                Toast.LENGTH_SHORT
            ).show()
        }
        holder.incrementItemQty.setOnClickListener {
            count++
            holder.noOfItemTV.text = count.toString()
            itemList[holder.adapterPosition].setNoOfItem(count)
            val price =
                "Rs: " + String.valueOf(itemList[holder.adapterPosition].getSingleItemPrice() * count)
            holder.priceTV!!.setText(price)
        }

        if (flag == 0) {
            holder.listItemQtyLL!!.setVisibility(View.INVISIBLE)
        }
        if (flag == SHOPPING_CART_ACT) {
            holder.addToCartTV.visibility = View.INVISIBLE
            holder.imageAndDetailLL!!.setOrientation(LinearLayout.HORIZONTAL)
            holder.sellingItemImageOfListItem.layoutParams.width = 140
            holder.sellingItemImageOfListItem.layoutParams.height = 140
            holder.detailLL!!.setMinimumWidth(LinearLayout.LayoutParams.MATCH_PARENT)
            holder.detailLL!!.setMinimumHeight(LinearLayout.LayoutParams.WRAP_CONTENT)
            holder.sellingItemNameOfListItem.gravity = Gravity.CENTER
            holder.itemDescriptionTV!!.setGravity(Gravity.CENTER)
            holder.listItemQtyLL!!.setGravity(Gravity.CENTER)
            holder.priceAndCartLL!!.setGravity(Gravity.CENTER)
            holder.detailLL!!.setPadding(34, 0, 0, 0)
        }

        holder.addToCartTV.setOnClickListener {
            if (flag == 0) {
                itemList[holder.adapterPosition].setNoOfItem(count)
                shoppingList.add(itemList[holder.adapterPosition])
                Toast.makeText(context, "Item added to the cart", Toast.LENGTH_SHORT).show()
            }
        }

        holder.itemCardViewList_Item!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, ViewItem::class.java)
            if (itemList[holder.adapterPosition].getImage2() != null) {
                intent.putExtra("IMAGE2", itemList[holder.adapterPosition].getImage2().toString())
            } else {
                intent.putExtra("IMAGE2", "")
            }
            intent.putExtra("IMAGE1", itemList[holder.adapterPosition].getImage1())
            intent.putExtra("ITEM_NAME", itemList[holder.adapterPosition].getItemName())
            intent.putExtra(
                "ITEM_DESCRIPTION",
                itemList[holder.adapterPosition].getItemDescription()
            )
            intent.putExtra("ITEM_PRICE", itemList[holder.adapterPosition].getSingleItemPrice())
            intent.putExtra("ITEM_QTY", itemList[holder.adapterPosition].getNoOfItem())
            context.startActivity(intent)
            Log.d("NOTHING", "ABCDEFGIJKLMNOPQRSTUVWXYZ")
        })
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sellingItemImageOfListItem: ImageView
        var sellingItemNameOfListItem: TextView
        var decrementItemQty: TextView
        var incrementItemQty: TextView
        var addToCartTV: CircleImageView
        var noOfItemTV: TextView
        var listItemQtyLL: LinearLayout? = null
        var imageAndDetailLL: LinearLayout? = null
        var detailLL: LinearLayout? = null
        var itemDescriptionTV: TextView? = null
        var priceAndCartLL: LinearLayout? = null
        var itemCardViewList_Item: MaterialCardView? = null
        var priceTV: TextView? = null

        init {
            sellingItemImageOfListItem = itemView.findViewById<ImageView>(R.id.sellingItemImageOfListItem)
            sellingItemNameOfListItem = itemView.findViewById<TextView>(R.id.sellingItemNameOfListItem)
            decrementItemQty = itemView.findViewById<TextView>(R.id.decrementItemQty)
            incrementItemQty = itemView.findViewById<TextView>(R.id.incrementItemQty)
            addToCartTV = itemView.findViewById<CircleImageView>(R.id.addToCartTV)
            noOfItemTV = itemView.findViewById<TextView>(R.id.noOfItemTV)

            //changes are done from below
            listItemQtyLL = itemView.findViewById(R.id.listItemQtyLL)
            imageAndDetailLL = itemView.findViewById<LinearLayout>(R.id.imageAndDetailLL)
            detailLL = itemView.findViewById<LinearLayout>(R.id.detailLL)
            itemDescriptionTV = itemView.findViewById<TextView>(R.id.itemDescriptionTV)
            priceAndCartLL = itemView.findViewById<LinearLayout>(R.id.priceAndCartLL)
            itemCardViewList_Item =
                itemView.findViewById<MaterialCardView>(R.id.itemCardViewList_Item)
            priceTV = itemView.findViewById<TextView>(R.id.priceTV)
        }
    }


}
