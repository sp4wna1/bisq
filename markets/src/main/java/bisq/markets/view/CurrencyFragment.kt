package bisq.markets.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import bisq.local.Resource.Status.*
import bisq.markets.databinding.FragmentCurrencyBinding
import bisq.markets.module.marketsModule
import bisq.markets.view.adapter.CurrencyAdapter
import bisq.markets.viewmodel.CurrencyViewModel
import br.com.elitma.remote.remoteModule
import kotlinx.coroutines.InternalCoroutinesApi
import network.bisq.R
import network.bisq.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules


class CurrencyFragment : BaseFragment() {

    private val viewModel: CurrencyViewModel by viewModel()
    private lateinit var binding: FragmentCurrencyBinding

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
        savedInstanceState: Bundle?,
    ): View? = FragmentCurrencyBinding.inflate(inflater, container, false).apply {
        binding = this

        initViews()
        binding.root.findNavController().navigate(R.id.action_curracyFragment_to_assetFragment)

    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currencies.observe(viewLifecycleOwner) {
            when (it.status) {
                SUCCESS -> binding.coinsList.adapter = CurrencyAdapter(it.data ?: emptyList())
                ERROR -> {
                }
                LOADING -> {
                }
            }
        }
    }

    private fun initViews() {
        binding.searchCoin.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {


                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("Not yet implemented")
            }

        })
    }


}

