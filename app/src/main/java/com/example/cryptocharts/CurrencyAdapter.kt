package com.example.cryptocharts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.currency_profile.view.*

var currencySym: String = ""
var currencyPrice: Double? = 0.0

class CurrencyAdapter (itemList: List<CurrencyModel>) : RecyclerView.Adapter<CurrencyAdapter.ItemHolder>() {
    private var items: List<CurrencyModel>

    init {
        this.items = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemHolder(inflater.inflate(R.layout.currency_profile, parent, false))

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val atIndex = items[position]
        currencySym = atIndex.symbol
//        val startIndexOfBracket = item.indexOf('(')
//        val endIndexOfBracket = item.length
//        val symbol = item.substring(startIndexOfBracket + 1, endIndexOfBracket - 1)

        holder.bind(atIndex)
        holder.itemView.setOnClickListener { v ->
            val expanded = atIndex.expanded
            val priceThread = PriceThread()
            priceThread.start()
            priceThread.join()
            atIndex.expanded = (expanded.not())
            atIndex.price = currencyPrice
            println(atIndex.price)
            notifyItemChanged(position)
        }

//        holder.itemView.setOnClickListener { v ->
//            val expanded = item.expanded
//            item.expanded = (expanded.not())
//            notifyItemChanged(position)
//        }

    }


    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView
        private val value: TextView
        private val subitems: View
        private val expand: View

        init {
            name = itemView.findViewById(R.id.name)
            value = itemView.findViewById(R.id.value)
            subitems = itemView.findViewById(R.id.subitems)
            expand = itemView.findViewById(R.id.expanded)
        }

        fun bind(item: CurrencyModel) {
            val expanded = item.expanded
            expand.visibility = if (!expanded) View.VISIBLE else View.GONE
            subitems.visibility = if (expanded) View.VISIBLE else View.GONE
            name.setText(item.name)
            value.setText(item.price.toString())
        }
    }
}

class PriceThread : Thread() {
    public override fun run() {

        val service2 = RetrofitClientInstance.retrofitInstance?.create(GetCurrencyService::class.java)
        val call = service2?.getCadValue(currencySym, CURRENCYTYPE)
        val currencyValue = call?.execute()?.body()?.CAD
        println(currencyValue)
        currencyPrice = currencyValue
    }

}