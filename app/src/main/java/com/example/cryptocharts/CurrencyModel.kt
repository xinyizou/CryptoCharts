package com.example.cryptocharts

class CurrencyModel (name: String, price: Double ?= 0.0, isFavorited: Boolean) {
    var name: String ?= ""
    var price: Double ?= 0.0
    var expanded: Boolean = false
    var isFavorited: Boolean ?= false
    val startIndexOfBracket = name.indexOf('(')
    val endIndexOfBracket = name.length
    val symbol = name.substring(startIndexOfBracket + 1, endIndexOfBracket - 1)

    init {
        this.name = name
        this.price = price
        this.isFavorited = isFavorited

    }


}