package bisq.markets.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import bisq.local.Currency
import bisq.markets.databinding.ItemCurrencyBinding

internal class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    private val currencies = mutableListOf<Currency>()

    fun setData(currencies: List<Currency>) {
        this.currencies.clear()
        this.currencies.addAll(currencies)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = currencies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setUp(currencies[position])
    }

    internal class ViewHolder(
        private val binding: ItemCurrencyBinding,
    ) : RecyclerView.ViewHolder(binding.root) {


        fun setUp(currency: Currency) {
            binding.name.text = currency.name

            binding.root.setOnClickListener {
                binding.root.findNavController()
                    .navigate(network.bisq.R.id.action_currencyFragment_to_assetFragment)
            }
        }

    }
}

