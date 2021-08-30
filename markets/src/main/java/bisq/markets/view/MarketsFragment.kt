package bisq.markets.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer
import bisq.local.Resource
import bisq.markets.databinding.FragmentMarketsBinding
import bisq.markets.module.marketsModule
import bisq.markets.view.adapter.AsksAdapter
import bisq.markets.view.adapter.BidsAdapter
import bisq.markets.view.adapter.CurrencyAdapter
import bisq.markets.viewmodel.MarketsViewModel
import br.com.elitma.remote.remoteModule
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
        loadKoinModules(listOf(remoteModule, marketsModule))
    }

    @InternalCoroutinesApi
    override fun onDetach() {
        super.onDetach()
        unloadKoinModules(listOf(remoteModule, marketsModule))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMarketsBinding.inflate(inflater, container, false).apply {
        binding = this

        initViews()
        observeDepth()
        observeCurrencies()

        // TODO Remove this
        viewModel.fetchCurrencies()
    }.root


    private fun initViews() {
        binding.bids.adapter = BidsAdapter()
        binding.asks.adapter = AsksAdapter()
        binding.currencies.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val currency = (binding.currencies.adapter as CurrencyAdapter).getItem(position)
                Log.d("GDP", "OnItemSelected btc_${currency.code.toLowerCase()}")
                viewModel.fetchOffers("btc_${currency.code.toLowerCase()}")
            }
        }
    }

    private fun observeCurrencies() {
        viewModel.currencies.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.ERROR -> {
                }
                Resource.Status.SUCCESS -> {
                    Log.d("GDP", "${it.data}")
                    binding.currencies.adapter = CurrencyAdapter(it.data ?: emptyList())
                }
            }
        })
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

                    (binding.bids.adapter as BidsAdapter).clear()
                    (binding.asks.adapter as AsksAdapter).clear()

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