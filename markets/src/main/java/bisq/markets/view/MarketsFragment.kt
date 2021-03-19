package bisq.markets.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import bisq.local.Resource
import bisq.markets.databinding.FragmentMarketsBinding
import bisq.markets.module.marketsModule
import bisq.markets.view.adapter.AsksAdapter
import bisq.markets.view.adapter.BidsAdapter
import bisq.markets.viewmodel.MarketsViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import network.bisq.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class MarketsFragment : BaseFragment() {

    private lateinit var binding: FragmentMarketsBinding

    private val viewModel: MarketsViewModel by viewModel()


    @InternalCoroutinesApi
    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadKoinModules(marketsModule)
    }

    @InternalCoroutinesApi
    override fun onDetach() {
        super.onDetach()
        unloadKoinModules(marketsModule)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMarketsBinding.inflate(inflater, container, false).apply {
        binding = this

        initViews()
        observeDepth()

        // TODO Remove this
        viewModel.fetchOffers("btc_brl")
    }.root

    private fun initViews() {
        binding.bids.adapter = BidsAdapter()
        binding.asks.adapter = AsksAdapter()
    }

    private fun observeDepth() {
        viewModel.offers.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.bidsProgress.visibility = View.VISIBLE
                    binding.asksProgress.visibility = View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    binding.bidsProgress.visibility = View.GONE
                    binding.asksProgress.visibility = View.GONE
                }
                Resource.Status.SUCCESS -> {
                    binding.bidsProgress.visibility = View.GONE
                    binding.asksProgress.visibility = View.GONE
                    (binding.bids.adapter as BidsAdapter).setData(it.data!!.first.filter {
                        it.direction == "BUY"
                    })
                    (binding.asks.adapter as AsksAdapter).setData(it.data!!.second.filter {
                        it.direction == "SELL"
                    })
                }
            }
        })
    }
}