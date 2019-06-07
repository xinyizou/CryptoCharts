package com.example.cryptocharts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CurrencyAdapter (itemList: List<String>) : RecyclerView.Adapter<CurrencyAdapter.ItemHolder>() {
    private var items: List<String>

    init {
        this.items = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemHolder(inflater.inflate(R.layout.currency_profile, parent, false))

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val item = items[position]

        holder.bind(item)

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
//        private val value: TextView

        init {
            name = itemView.findViewById(R.id.name)
//            value = itemView.findViewById(R.id.value)
        }

        fun bind(item: String) {
            name.setText(item)
        }
    }
}