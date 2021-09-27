package bisq.android.chart

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import bisq.android.chart.databinding.FragmentCandleBinding
import bisq.local.Resource
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.google.android.material.tabs.TabLayout
import network.bisq.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.math.BigDecimal
import java.text.NumberFormat

class CandleFragment : BaseFragment() {

    private lateinit var binding: FragmentCandleBinding
    private val viewModel: ChartsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentCandleBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val numberFormat = NumberFormat.getCurrencyInstance()

        numberFormat.maximumFractionDigits = 0;


        viewModel.tick.observe(viewLifecycleOwner){
            binding.chronometer.text = it.toString()
        }

        viewModel.getTickers(getPair(requireArguments()))
        viewModel.tickers.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {

                }
                Resource.Status.ERROR -> {

                }
                Resource.Status.SUCCESS -> {
                    val ticker = it.data
                    val convert = numberFormat.format(BigDecimal(ticker?.last))
                    binding.valueUSD.text = convert.toString()


                }
            }
        }

        viewModel.volumes.observe(viewLifecycleOwner) {
            when (it.status){
                Resource.Status.LOADING -> {
                    binding.progressBarChart.visibility = View.VISIBLE
                    binding.barChart.visibility = View.INVISIBLE


                }
                Resource.Status.ERROR ->{
                    binding.progressBarChart.visibility = View.INVISIBLE
                    binding.barChart.visibility = View.INVISIBLE


                }
                Resource.Status.SUCCESS -> {
                    binding.barChart.visibility = View.VISIBLE
                    binding.progressBarChart.visibility = View.INVISIBLE


                    val xval1 = binding.barChart.xAxis
                    xval1.position = XAxis.XAxisPosition.BOTTOM
                    xval1.setDrawGridLines(false)

                    val volumes = it.data

                    val barList = volumes?.mapIndexed { index, it -> BarEntry(index.toFloat(),it.volume.toFloat()) } ?: emptyList()



                    binding.barChart.xAxis.gridColor = ContextCompat.getColor(requireContext(), R.color.grid_color)
                    binding.barChart.axisLeft.gridColor = ContextCompat.getColor(requireContext(), android.R.color.darker_gray)
                    binding.barChart.axisRight.gridColor = ContextCompat.getColor(requireContext(), R.color.grid_color)
                    binding.barChart.xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.grid_color)
                    binding.barChart.axisLeft.isEnabled = false
                    binding.barChart.axisRight.textColor = ContextCompat.getColor(requireContext(), R.color.grid_color)
                    binding.barChart.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.background_color))

                    val barDataSet = BarDataSet(barList, "teste")
                    barDataSet.color = Color.rgb(80, 80, 80)
                    barDataSet.valueTextColor = ContextCompat.getColor(requireContext(), android.R.color.white)


                    binding.barChart.data = BarData(barDataSet)

                }
            }
        }
        viewModel.candles.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressCandle.visibility = View.VISIBLE
                    binding.candleChart.visibility = View.INVISIBLE
                }
                Resource.Status.ERROR -> {
                    binding.progressCandle.visibility = View.INVISIBLE
                    binding.candleChart.visibility = View.INVISIBLE

                }
                Resource.Status.SUCCESS -> {
                    binding.progressCandle.visibility = View.INVISIBLE
                    binding.candleChart.visibility = View.VISIBLE
                    binding.candleChart.invalidate()

                    val candles = it.data
                    if (candles.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), "NullOrEmpty", Toast.LENGTH_LONG).show()
                    } else {
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

                        binding.candleChart.xAxis.setDrawGridLines(true)
                        binding.candleChart.xAxis.gridColor = ContextCompat.getColor(requireContext(), R.color.grid_color)
                        binding.candleChart.axisLeft.gridColor = ContextCompat.getColor(requireContext(), android.R.color.darker_gray)
                        binding.candleChart.axisRight.gridColor = ContextCompat.getColor(requireContext(), R.color.grid_color)
                        binding.candleChart.xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.grid_color)
                        binding.candleChart.axisLeft.isEnabled = false
                        binding.candleChart.axisRight.textColor = ContextCompat.getColor(requireContext(), R.color.grid_color)
                        binding.candleChart.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.background_color))
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
                        candleDataSet.valueTextColor = ContextCompat.getColor(requireContext(), android.R.color.white)
                        candleDataSet.shadowColorSameAsCandle = true
                        candleDataSet.shadowWidth = 1f
                        candleDataSet.decreasingColor = ContextCompat.getColor(requireContext(), R.color.red_candle)
                        candleDataSet.decreasingPaintStyle = Paint.Style.FILL
                        candleDataSet.increasingColor = ContextCompat.getColor(requireContext(), R.color.green_candle)
                        candleDataSet.increasingPaintStyle = Paint.Style.FILL

                        binding.candleChart.data = CandleData(candleDataSet)

                    }
                }
            }
        }
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.getCharts(getPair(requireArguments()), CandleInterval.values().get(tab?.position ?: 0))
                viewModel.getVolumes(getPair(requireArguments()), CandleInterval.values().get(tab?.position ?: 0))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        }
        )

        CandleInterval.values().forEach { binding.tabs.addTab(binding.tabs.newTab().setText(it.value)) }

    }

    private fun getPair(arguments: Bundle) = arguments.getString("pair") ?: ""

}
