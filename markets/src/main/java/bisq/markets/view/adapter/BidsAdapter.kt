package bisq.markets.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bisq.markets.databinding.ItemBidBinding
import java.math.BigDecimal

internal class BidsAdapter : RecyclerView.Adapter<BidsAdapter.BidsViewHolder>() {

    private val bids = mutableListOf<BigDecimal>()

    fun setData(bids: List<BigDecimal>) {
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

        fun bind(value: BigDecimal) {
            binding.value.text = "$value"
        }
    }
}