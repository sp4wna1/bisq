package bisq.markets.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bisq.markets.databinding.FragmentMarketsBinding
import network.bisq.base.BaseFragment

class MarketsFragment : BaseFragment() {

    private lateinit var binding: FragmentMarketsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMarketsBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root
}