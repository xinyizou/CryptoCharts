package com.example.cryptocharts

import com.example.cryptocharts.dto.CurrencyList
import com.example.cryptocharts.dto.CurrencyNameDTO
import com.example.cryptocharts.dto.CurrencyValue
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetCurrencyService {

    @GET("all/coinlist")
    fun getAllCurrencies() : Call<CurrencyList>

    @GET("price")
    fun getCadValue(@Query("fsym") shortName : String, @Query("tsyms") currencyType : String) : Call<CurrencyValue>

//    @GET("price?fsym=ETH&tsyms=CAD")
//    fun getCadValue() : Call<CurrencyValue>

//    https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=CAD
//    https://min-api.cryptocompare.com/data/all/coinlist
}