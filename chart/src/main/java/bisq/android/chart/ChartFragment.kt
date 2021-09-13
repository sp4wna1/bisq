package bisq.android.chart

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import bisq.android.chart.databinding.FragmentChartBinding
import bisq.local.Resource
import br.com.elitma.remote.remoteModule
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.google.android.material.tabs.TabLayout
import network.bisq.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class ChartFragment : BaseFragment() {

    private lateinit var binding: FragmentChartBinding

    private val viewModel: ChartsViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadKoinModules(listOf(remoteModule, chartModule))
    }

    override fun onDetach() {
        super.onDetach()
        unloadKoinModules(listOf(remoteModule, chartModule))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentChartBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.candles.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.ERROR -> {
                }
                Resource.Status.SUCCESS -> {
                    binding.candleChart.invalidate()


                    val candles = it.data
                    if (candles.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), "NullOrEmpty", Toast.LENGTH_LONG).show()
                    } else {
                        // X values
                        val xvalue = ArrayList<String>()
                        xvalue.add("10:00 AM")
                        xvalue.add("11:00 AM")
                        xvalue.add("12:00 AM")
                        xvalue.add("3:00 PM")
                        xvalue.add("5:00 PM")
                        xvalue.add("8:00 PM")
                        xvalue.add("10:00 PM")

                        val xval = binding.candleChart.xAxis
                        xval.position = XAxis.XAxisPosition.BOTTOM
                        xval.setDrawGridLines(false)


                        binding.candleChart.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                android.R.color.white
                            )
                        )

                        val candleList = candles.mapIndexed { index, candleResponse ->
                            CandleEntry(
                                index.toFloat(),
                                candleResponse.high.toFloat(),
                                candleResponse.low.toFloat(),
                                candleResponse.open.toFloat(),
                                candleResponse.close.toFloat()
                            )
                        }

                        val candleDataSet = CandleDataSet(candleList, "first")
                        candleDataSet.color = Color.rgb(80, 80, 80)
                        candleDataSet.shadowColor = ContextCompat.getColor(
                            requireContext(),
                            android.R.color.darker_gray
                        )
                        candleDataSet.shadowWidth = 1f
                        candleDataSet.decreasingColor =
                            ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark)
                        candleDataSet.decreasingPaintStyle = Paint.Style.FILL


                        candleDataSet.increasingColor = ContextCompat.getColor(
                            requireContext(),
                            android.R.color.holo_green_light
                        )
                        candleDataSet.increasingPaintStyle = Paint.Style.FILL


                        binding.candleChart.data = CandleData(candleDataSet)
                    }
                }
            }
        }
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.getCharts(
                    getPair(requireArguments()),
                    CandleInterval.values().get(tab?.position ?: 0)
                )
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })



        CandleInterval.values().forEach {
            binding.tabs.addTab(binding.tabs.newTab().setText(it.value))
        }
    }

    private fun getPair(arguments: Bundle) = arguments.getString("pair") ?: ""

}

