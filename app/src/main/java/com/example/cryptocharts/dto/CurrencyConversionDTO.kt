package com.example.cryptocharts.dto

data class CurrencyConversionDTO(var btc: Double = 0.03131, var usd: Double = 249.87, var eur: Double = 222.18) {
    constructor() : this(0.0, 0.0, 0.0)
}