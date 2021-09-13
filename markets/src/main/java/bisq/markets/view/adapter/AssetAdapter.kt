package bisq.markets.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import bisq.local.Currency
import bisq.markets.databinding.ItemAssetBinding
import bisq.markets.view.AssetFragmentDirections

internal class AssetAdapter(private val currencyCode: String?) :
    RecyclerView.Adapter<AssetAdapter.ViewHolder>() {

    private val assets = mutableListOf<Currency>()

    fun setData(assets: List<Currency>) {
        this.assets.clear()
        this.assets.addAll(assets)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = assets.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemAssetBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setUp(assets[position])
    }

    internal inner class ViewHolder(
        private val binding: ItemAssetBinding,
    ) : RecyclerView.ViewHolder(binding.root) {


        fun setUp(currency: Currency) {
            binding.name.text = currency.name

            binding.root.setOnClickListener {
                it.findNavController()
                    .navigate(AssetFragmentDirections.actionAssetFragmentToChartFragment("${currency.code}_${currencyCode}"))
            }
        }

    }
}