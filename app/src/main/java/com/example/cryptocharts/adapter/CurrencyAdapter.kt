package com.example.cryptocharts.adapter

import android.os.SystemClock
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.cryptocharts.R
import com.example.cryptocharts.activities.CURRENCYTYPE
import com.example.cryptocharts.activities.allCurrencies
import com.example.cryptocharts.dto.CurrencyModel
import com.example.cryptocharts.retrofit.GetCurrencyService
import com.example.cryptocharts.retrofit.RetrofitClientInstance
import kotlinx.android.synthetic.main.currency_profile.view.*

var currencySym: String = ""
var currencyPrice: Double? = 0.0
var numberOfFavorites: Int = 0

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

        holder.bind(atIndex)
        holder.itemView.setOnClickListener { v ->
            if (atIndex.price == 0.1) {
                currencySym = atIndex.symbol
                val priceThread = PriceThread()
                priceThread.start()
                priceThread.join()
                atIndex.price = currencyPrice
            }
            val expanded = atIndex.expanded
            atIndex.expanded = (expanded.not())
            notifyItemChanged(position)
        }

        holder.itemView.favorited.setSafeOnClickListener { v ->

            val isFavourite = atIndex.isFavorited
            atIndex.isFavorited = (isFavourite.not())
            if (isFavourite) {
                allCurrencies.add(numberOfFavorites, atIndex)
                allCurrencies.removeAt(position)
                notifyItemMoved(position, numberOfFavorites - 1)
                notifyItemChanged(position)
                notifyItemChanged(numberOfFavorites - 1)
                notifyDataSetChanged()
                numberOfFavorites = numberOfFavorites - 1
            }
            else {
                allCurrencies.removeAt(position)
                allCurrencies.add(0, atIndex)
                notifyItemMoved(position, 0)
                notifyItemChanged(0)
                notifyItemChanged(position)
                notifyDataSetChanged()
                numberOfFavorites = numberOfFavorites + 1
            }
        }

    }


    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView
        private val value: TextView
        private val favourited: ImageView
        private val subitems: View
        private val expand: View

        init {
            name = itemView.findViewById(R.id.name)
            value = itemView.findViewById(R.id.value)
            subitems = itemView.findViewById(R.id.subitems)
            expand = itemView.findViewById(R.id.expanded)
            favourited = itemView.findViewById(R.id.favorited)
        }

        fun bind(item: CurrencyModel) {
            val expanded = item.expanded
            val isFavorited = item.isFavorited
            expand.visibility = if (!expanded) View.VISIBLE else View.GONE
            subitems.visibility = if (expanded) View.VISIBLE else View.GONE
            if (isFavorited) {
                favourited.setImageResource(R.drawable.ic_star_black_24dp)
            }
            else {
                favourited.setImageResource(R.drawable.ic_star_border_black_24dp)
            }

            name.setText(item.name)
            value.setText("$" + item.price.toString() + " CAD")
        }
    }
}

class PriceThread : Thread() {
    override fun run() {

        val service2 = RetrofitClientInstance.retrofitInstance?.create(GetCurrencyService::class.java)
        val call = service2?.getCadValue(currencySym, CURRENCYTYPE)
        val currencyValue = call?.execute()?.body()?.CAD
        println(currencyValue)
        currencyPrice = currencyValue

    }

}

class SafeClickListener(
    private var defaultInterval: Int = 2000,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}