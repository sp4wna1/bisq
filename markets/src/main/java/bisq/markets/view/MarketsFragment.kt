package bisq.markets.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bisq.markets.databinding.FragmentMarketsBinding
import bisq.markets.viewmodel.MarketsViewModel
import network.bisq.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MarketsFragment : BaseFragment() {

    private lateinit var binding: FragmentMarketsBinding

    private val viewModel: MarketsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMarketsBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root
}