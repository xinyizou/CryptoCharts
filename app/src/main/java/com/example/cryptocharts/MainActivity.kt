package com.example.cryptocharts

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import retrofit2.adapter.rxjava2.Result.response
import com.google.gson.GsonBuilder



class MainActivity : AppCompatActivity() {

    private var allCurrencies = ArrayList<String>()
    var adapter = CurrencyAdapter(allCurrencies)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.list)

        val service = RetrofitClientInstance.retrofitInstance?.create(GetCurrencyService::class.java)
        val call = service?.getAllCurrencies()
        call?.enqueue(object : Callback<CurrencyList> {

            override fun onResponse(call: Call<CurrencyList>, response: Response<CurrencyList>) {
                Log.w(
                    "gson => ",
                    GsonBuilder().setPrettyPrinting().create().toJson(response)
                )

                val body = response.body()
                val map = body!!.Data
                val keys = map.keys

                for (key in keys) {
                    val value = map.getValue(key)

                    val startIndexOfBracket = value.FullName.indexOf('(')
                    val endIndexOfBracket = value.FullName.length
                    val symbol = value.FullName.substring(startIndexOfBracket + 1, endIndexOfBracket - 1)

                    println(value.FullName)
                    println(symbol)
                    allCurrencies.add(value.FullName)
                }

                recycler.layoutManager = LinearLayoutManager(this@MainActivity)
                recycler.adapter = adapter
                recycler.itemAnimator = DefaultItemAnimator()
                recycler.setHasFixedSize(true)

            }

            override fun onFailure(call: Call<CurrencyList>, t: Throwable) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG)

            }
        })

    }

//    class CurrencyAdapter(val currencies : List<String>, val itemLayout : Int) : RecyclerView.Adapter<CurrencyViewHolder>() {
//        override fun getItemCount(): Int {
//            return currencies.size
//        }
//
//        override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
//            var currency = currencies[position]
//            holder.updateCurrency(currency)
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
//            var view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
//            return CurrencyViewHolder(view)
//        }
//    }
//
//    class CurrencyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
//        private var name : TextView?
//
////        private var value : TextView
//
//        init {
//            name = itemView.findViewById(R.id.name)
////            value = itemView.findViewById(R.id.value)
//        }
//
//        fun updateCurrency(currency: String) {
//            name?.text = currency
//        }
//    }

    /*
     get CAD value
     for all cryptocoins {
     val url = "https://min-api.cryptocompare.com/data/price?fsym=" + cryptoName + "&tsyms=CAD"
     }
      */



}
