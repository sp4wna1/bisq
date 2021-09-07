package bisq.markets.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import bisq.local.Resource
import bisq.markets.databinding.FragmentAssetBinding
import bisq.markets.view.adapter.AssetAdapter
import bisq.markets.viewmodel.AssetViewModel
import network.bisq.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AssetFragment : BaseFragment() {

    private lateinit var binding: FragmentAssetBinding

    private val viewModel: AssetViewModel by viewModel()

    private val adapter = AssetAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentAssetBinding.inflate(inflater, container, false).apply {
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
        binding.searchAsset.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
                Resource.Status.SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    adapter.setData(it.data ?: emptyList())
                }
                Resource.Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                }
            }
        }
    }
}