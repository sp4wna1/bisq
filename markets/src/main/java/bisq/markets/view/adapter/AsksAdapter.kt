package bisq.markets.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bisq.markets.databinding.ItemAskBinding
import java.math.BigDecimal

internal class AsksAdapter : RecyclerView.Adapter<AsksAdapter.AsksViewHolder>() {

    private val asks = mutableListOf<BigDecimal>()

    fun setData(asks: List<BigDecimal>) {
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

        fun bind(value: BigDecimal) {
            binding.value.text = "$value"
        }
    }
}