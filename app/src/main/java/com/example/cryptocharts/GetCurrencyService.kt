package com.example.cryptocharts

import com.example.cryptocharts.dto.CurrencyList
import com.example.cryptocharts.dto.CurrencyNameDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GetCurrencyService {

    @GET("all/coinlist")
    fun getAllCurrencies() : Call<CurrencyList>

    @GET("price?fsym={shortName}&tsyms=CAD")
    fun getCadValue(@Path(value = "shortName") shortName : String) : Call<Double>
}