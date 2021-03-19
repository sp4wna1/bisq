package bisq.markets.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bisq.local.Offer
import bisq.markets.databinding.ItemBidBinding
import network.bisq.toNumber

internal class BidsAdapter : RecyclerView.Adapter<BidsAdapter.BidsViewHolder>() {

    private val bids = mutableListOf<Offer>()

    fun setData(bids: List<Offer>) {
        this.bids.clear()
        this.bids.addAll(bids)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = bids.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BidsViewHolder =
        BidsViewHolder(
            ItemBidBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BidsViewHolder, position: Int) {
        holder.bind(bids[position])
    }

    internal class BidsViewHolder(private val binding: ItemBidBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(offer: Offer) {
            binding.value.text = offer.price.toNumber(0)
            binding.volume.text = offer.volume.toNumber(4)
        }
    }
}