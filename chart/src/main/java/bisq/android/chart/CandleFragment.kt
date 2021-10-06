package bisq.android.chart

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import bisq.android.chart.databinding.FragmentCandleBinding
import bisq.local.Resource
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import network.bisq.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
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
       // viewModel.getTickers()
        viewModel.getCharts(requireArguments().getString("pair") ?: "", CandleInterval.DAY)
        viewModel.candles.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.candleChart.visibility = View.INVISIBLE
                }
                Resource.Status.ERROR -> {
                    binding.candleChart.visibility = View.INVISIBLE

                }
                Resource.Status.SUCCESS -> {
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
    }
}