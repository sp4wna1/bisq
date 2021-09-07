package bisq.markets.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import bisq.markets.databinding.FragmentAssetBinding
import bisq.markets.view.adapter.AssetAdapter
import network.bisq.base.BaseFragment

class AssetFragment : BaseFragment() {

    private lateinit var binding: FragmentAssetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentAssetBinding.inflate(inflater, container, false).apply {
        binding = this

        initViews()

    }.root

    private fun initViews() {
        binding.coinsList.adapter = AssetAdapter(emptyList())

        binding.searchAsset.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("Not yet implemented")
            }
        })
    }
}