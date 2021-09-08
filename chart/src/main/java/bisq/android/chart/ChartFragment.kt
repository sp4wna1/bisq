package bisq.android.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bisq.android.chart.databinding.FragmentChartBinding
import network.bisq.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class CurrencyFragment : BaseFragment() {

    private lateinit var binding: FragmentChartBinding

    private val viewModel: ChartsViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentChartBinding.inflate(inflater, container, false).apply {
        binding = this

    }.root



}


