package com.example.e_commercek.data

class USER_MODEL {
    private var Name: String? = null
    private var Email: String? = null
    private var Phone: String? = null
    private var Address: String? = null

    constructor(name: String?, email: String?, phone: String?, address: String?) {
        this.Name = name
        this.Phone = phone
        this.Address = address
        this.Email = email
    }

    constructor() {}

    fun getName(): String? {
        return Name
    }

    fun getEmail(): String? {
        return Email
    }

    fun setEmail(email: String) {
        Email = email
    }

    fun setName(name: String) {
        Name = name
    }

    fun getPhone(): String? {
        return Phone
    }

    fun setPhone(phone: String) {
        Phone = phone
    }

    fun getAddress(): String? {
        return Address
    }

    fun setAddress(address: String) {
        Address = address
    }
}
