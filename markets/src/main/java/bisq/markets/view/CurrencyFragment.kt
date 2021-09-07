package bisq.markets.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import bisq.local.Resource.Status.*
import bisq.markets.databinding.FragmentCurrencyBinding
import bisq.markets.module.marketsModule
import bisq.markets.view.adapter.CurrencyAdapter
import bisq.markets.viewmodel.CurrencyViewModel
import br.com.elitma.remote.remoteModule
import kotlinx.coroutines.InternalCoroutinesApi
import network.bisq.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

@InternalCoroutinesApi
class CurrencyFragment : BaseFragment() {

    private val viewModel: CurrencyViewModel by viewModel()

    private val adapter = CurrencyAdapter()

    private lateinit var binding: FragmentCurrencyBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadKoinModules(listOf(remoteModule, marketsModule))
    }

    override fun onDetach() {
        super.onDetach()
        unloadKoinModules(listOf(remoteModule, marketsModule))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentCurrencyBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeCurrencies()
    }


    private fun initViews() {
        viewModel.fetchCurrencies()

        binding.coinsList.adapter = adapter
        binding.searchCoin.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("Not yet implemented")
            }
        })
    }

    private fun observeCurrencies() {
        viewModel.currencies.observe(viewLifecycleOwner) {
            when (it.status) {
                SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    adapter.setData(it.data ?: emptyList())
                }
                ERROR -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG).show()
                }
                LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                }
            }
        }
    }
}

