package bisq.markets.view.adapter

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SpinnerAdapter
import android.widget.TextView
import bisq.local.Currency
import bisq.markets.R

internal class CurrencyAdapter(
    private val currencies: List<Currency>
) : SpinnerAdapter {


    override fun isEmpty(): Boolean = currencies.isEmpty()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return LayoutInflater.from(parent?.context)
            .inflate(android.R.layout.simple_spinner_item, parent, false)
    }

    override fun registerDataSetObserver(observer: DataSetObserver?) {

    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getItem(position: Int): Currency = currencies[position]

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long = currencies[position].hashCode().toLong()

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View =
        convertView?.apply {
            findViewById<TextView>(R.id.name).text =
                "${currencies[position].name} (${currencies[position].code})"
        }
            ?: LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_currency, parent, false).apply {
                    findViewById<TextView>(R.id.name).text =
                        "${currencies[position].name} (${currencies[position].code})"
                }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {

    }

    override fun getCount(): Int = currencies.size

}