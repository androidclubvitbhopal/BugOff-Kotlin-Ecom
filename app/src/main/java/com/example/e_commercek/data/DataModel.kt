package com.example.e_commercek.data

import android.net.Uri


class DataModel {
    private var image1:Int = 0
    private var image2: Uri? = null
    private var itemName: String? = null
    private var noOfItem:Int = 1
    private var itemDescription: String? = null
    private var singleItemPrice:Double = 0.0

    constructor(image1: Int, itemName: String?, itemDescription: String?,singleItemPrice: Double)
    {
        this.image1 = image1
        this.itemName = itemName
        this.itemDescription = itemDescription
        this.singleItemPrice = singleItemPrice
    }

    constructor(image2: Uri?, itemName: String?, itemDescription: String?, singleItemPrice: Double) {
        this.image2 = image2
        this.itemName = itemName
        this.itemDescription = itemDescription
        this.singleItemPrice = singleItemPrice
    }

    fun getItemDescription(): String? {
        return itemDescription
    }

    fun setItemDescription(itemDescription: String?) {
        this.itemDescription = itemDescription
    }

    fun getSingleItemPrice(): Double {
        return singleItemPrice
    }

    fun setSingleItemPrice(singleItemPrice: Double) {
        this.singleItemPrice = singleItemPrice
    }

    fun getImage1(): Int {
        return image1
    }

    fun setImage1(image1: Int) {
        this.image1 = image1
    }

    fun getImage2(): Uri? {
        return image2
    }

    fun setImage2(image2: Uri?) {
        this.image2 = image2
    }

    fun getItemName(): String? {
        return itemName
    }

    fun setItemName(itemName: String?) {
        this.itemName = itemName
    }

    fun getNoOfItem(): Int {
        return noOfItem
    }

    fun setNoOfItem(noOfItem: Int) {
        this.noOfItem = noOfItem
    }

}
