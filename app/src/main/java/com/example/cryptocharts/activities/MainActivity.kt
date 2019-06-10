package com.example.cryptocharts.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.cryptocharts.*
import com.example.cryptocharts.adapter.CurrencyAdapter
import com.example.cryptocharts.dto.CurrencyModel
import com.example.cryptocharts.retrofit.GetCurrencyService
import com.example.cryptocharts.retrofit.RetrofitClientInstance
import java.util.*

var allCurrencies = ArrayList<CurrencyModel>()
val CURRENCYTYPE = "CAD"

class MainActivity : AppCompatActivity() {

    var adapter = CurrencyAdapter(allCurrencies)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.list)

        val thread = NameThread()
        thread.start()
        thread.join()

        println("Hello" + allCurrencies.size)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.setHasFixedSize(true)

    }

}

class NameThread : Thread() {
    override fun run() {
        val service = RetrofitClientInstance.retrofitInstance?.create(GetCurrencyService::class.java)
        val call = service?.getAllCurrencies()
        val currencyList = call?.execute()?.body()

        val map = currencyList!!.Data
        val keys = map.keys

        for (key in keys) {
            val value = map.getValue(key)
            val name = value.FullName
            val currencyModel = CurrencyModel(name, 0.1, false)
            allCurrencies.add(currencyModel)
        }

    }

}