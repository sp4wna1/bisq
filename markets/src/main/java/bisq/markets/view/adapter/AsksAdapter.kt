package bisq.markets.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bisq.local.Offer
import bisq.markets.databinding.ItemAskBinding
import network.bisq.toNumber

internal class AsksAdapter : RecyclerView.Adapter<AsksAdapter.AsksViewHolder>() {

    private val asks = mutableListOf<Offer>()

    fun setData(asks: List<Offer>) {
        this.asks.clear()
        this.asks.addAll(asks)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = asks.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsksViewHolder =
        AsksViewHolder(
            ItemAskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AsksViewHolder, position: Int) {
        holder.bind(asks[position])
    }

    internal class AsksViewHolder(private val binding: ItemAskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(offer: Offer) {
            binding.value.text = offer.price.toNumber(0)
            binding.volume.text = offer.volume.toNumber(4)
        }
    }
}