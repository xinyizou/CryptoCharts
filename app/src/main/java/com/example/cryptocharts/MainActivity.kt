package com.example.cryptocharts

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.DefaultItemAnimator
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.cryptocharts.dto.CurrencyList
import com.example.cryptocharts.dto.CurrencyNameDTO
import com.example.cryptocharts.dto.CurrencyValue
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import retrofit2.adapter.rxjava2.Result.response
import com.google.gson.GsonBuilder
import java.io.IOException

public var allCurrencies = ArrayList<CurrencyModel>()
val CURRENCYTYPE = "CAD"

class MainActivity : AppCompatActivity() {

    var adapter = CurrencyAdapter(allCurrencies)

    val service2 = RetrofitClientInstance2.retrofitInstance?.create(GetCurrencyService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.list)

        val thread = NameThread()
        thread.start()
        thread.join()
//
//        val priceThread = PriceThread()
//        priceThread.start()
//        priceThread.join()

        println("Hello" + allCurrencies.size)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        println("Hilow")

    }

}

class NameThread : Thread() {
    public override fun run() {
        val service = RetrofitClientInstance.retrofitInstance?.create(GetCurrencyService::class.java)
        val call = service?.getAllCurrencies()
        val currencyList = call?.execute()?.body()

        val map = currencyList!!.Data
        val keys = map.keys

        for (key in keys) {
            val value = map.getValue(key)
            val name = value.FullName
            val startIndexOfBracket = name.indexOf('(')
            val endIndexOfBracket = name.length
            val symbol = name.substring(startIndexOfBracket + 1, endIndexOfBracket - 1)
            val call = service?.getCadValue(symbol, CURRENCYTYPE)
            val currencyValue = call?.execute()?.body()?.CAD
            val currencyModel = CurrencyModel(name, currencyValue, false)
            allCurrencies.add(currencyModel)
        }

    }

}
//
//class PriceThread : Thread() {
//    public override fun run() {
//
//        for (i in 1..5) {
//            val currency = allCurrencies[i]
//
//            val service2 = RetrofitClientInstance.retrofitInstance?.create(GetCurrencyService::class.java)
//            val call = service2?.getCadValue(currency.symbol, CURRENCYTYPE)
//            val currencyValue = call?.execute()?.body()?.CAD
//
//            currency.price = currencyValue
//        }
//
//    }
//
//}
