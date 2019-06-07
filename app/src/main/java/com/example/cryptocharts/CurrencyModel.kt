package com.example.cryptocharts

class CurrencyModel {
    var name: String = ""
    var price: String = ""
    var isFavorited: Boolean = false

    fun Item(name: String, price: String, isFavorited: Boolean) {
        this.name = name
        this.price = price
        this.isFavorited = isFavorited
    }

    fun getCurrencyName(): String {
        return name
    }

    fun getCurrencyPrice(): String {
        return price
    }

    fun getIsFavorited(): Boolean {
        return isFavorited
    }

}