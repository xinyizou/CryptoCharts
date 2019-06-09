package com.example.cryptocharts

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient



object RetrofitClientInstance {
    private var retrofit: Retrofit? = null
    private val CURRENCY_URL = "https://min-api.cryptocompare.com/data/"

    val retrofitInstance: Retrofit?

    get() {
        if (retrofit == null) {
            retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(CURRENCY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}

object RetrofitClientInstance2 {
    private var retrofit: Retrofit? = null
    private val CURRENCY_URL = "https://min-api.cryptocompare.com/data/"

    val retrofitInstance: Retrofit?

        get() {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(CURRENCY_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}

object ServiceGenerator {
    val API_BASE_URL = "https://min-api.cryptocompare.com/data/"
    private val httpClient = OkHttpClient.Builder()
    private val builder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }
}