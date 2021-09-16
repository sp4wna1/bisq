package bisq.android.chart

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import bisq.android.chart.databinding.FragmentLineChartBinding
import bisq.local.Resource
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.google.android.material.tabs.TabLayout
import network.bisq.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LineFragment : BaseFragment() {

    private val viewModel: ChartsViewModel by sharedViewModel()

    private val binding: FragmentLineChartBinding by lazy {
        FragmentLineChartBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTickers(getPair(requireArguments()))
        viewModel.tickers.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {

                }
                Resource.Status.ERROR -> {

                }
                Resource.Status.SUCCESS -> {
                    val ticker = it.data
                    binding.valueUSD.setText(ticker?.last?.toString())
                }
            }
        }
        viewModel.candles.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressCandle.visibility = View.VISIBLE
                    binding.lineChart.visibility = View.INVISIBLE

                }
                Resource.Status.ERROR -> {
                    binding.progressCandle.visibility = View.INVISIBLE
                    binding.lineChart.visibility = View.INVISIBLE


                }
                Resource.Status.SUCCESS -> {
                    binding.progressCandle.visibility = View.INVISIBLE
                    binding.lineChart.invalidate()
                    binding.lineChart.visibility = View.VISIBLE




                    val lines = it.data
                    if (lines.isNullOrEmpty()) {
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

                        val xval = binding.lineChart.xAxis
                        xval.position = XAxis.XAxisPosition.BOTTOM
                        xval.setDrawGridLines(false)



                        binding.lineChart.xAxis.setDrawGridLines(true)
                        binding.lineChart.xAxis.gridColor =
                            ContextCompat.getColor(requireContext(), R.color.grid_color)
                        binding.lineChart.axisLeft.gridColor = ContextCompat.getColor(
                            requireContext(), android.R.color.darker_gray

                        )
                        binding.lineChart.axisRight.gridColor =
                            ContextCompat.getColor(requireContext(), R.color.grid_color)
                        ContextCompat.getColor(requireContext(), R.color.grid_color)

                        binding.lineChart.xAxis.textColor =
                            ContextCompat.getColor(requireContext(), R.color.grid_color)
                        binding.lineChart.axisLeft.isEnabled = false
                        binding.lineChart.axisRight.textColor =
                            ContextCompat.getColor(requireContext(), R.color.grid_color)
                        binding.lineChart.setBackgroundColor(
                            ContextCompat.getColor(requireContext(), R.color.background_color)


                        )

                        val candleList = lines.mapIndexed { index, candleResponse ->
                            Entry(
                                index.toFloat(),
                                candleResponse.close.toFloat()
                            )
                        }

                        val lineDataSet = LineDataSet(candleList, "first")
                        lineDataSet.color = Color.rgb(80, 80, 80)
                        binding.lineChart.data = LineData(lineDataSet)
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
