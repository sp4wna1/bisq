package bisq.markets.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import bisq.local.Currency
import bisq.markets.databinding.ItemCurrencyBinding


internal class CurrencyAdapter(private val currencies: List<Currency>) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {



    override fun getItemCount(): Int {
        return currencies.size
    }


    class ViewHolder(private val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {


            fun setUp(currency: Currency){
                binding.name.setText(currency.name)

                binding.root.setOnClickListener {
                    binding.root.findNavController().navigate(network.bisq.R.id.action_currencyFragment_to_assetFragment)
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = currencies[position]
        holder.setUp(currency)
    }
}

