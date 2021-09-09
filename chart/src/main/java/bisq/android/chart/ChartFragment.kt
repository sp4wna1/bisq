package bisq.android.chart

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import bisq.android.chart.databinding.FragmentChartBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import network.bisq.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChartFragment : BaseFragment() {

    private lateinit var binding: FragmentChartBinding

    private val viewModel: ChartsViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentChartBinding.inflate(inflater, container, false).apply {
        binding = this
        setCandleStickChart()

    }.root

    fun setCandleStickChart() {
        // X values
        val xvalue = ArrayList<String>()
        xvalue.add("10:00 AM")
        xvalue.add("11:00 AM")
        xvalue.add("12:00 AM")
        xvalue.add("3:00 PM")
        xvalue.add("5:00 PM")
        xvalue.add("8:00 PM")
        xvalue.add("10:00 PM")

        //y  axis

        val candlesStickEntry = ArrayList<CandleEntry>()
        candlesStickEntry.add(CandleEntry(0f, 225.0f, 219.84f, 224.94f, 226.41f))
        candlesStickEntry.add(CandleEntry(1f, 228.0f, 222.14f, 223.00f, 212.41f))
        candlesStickEntry.add(CandleEntry(2f, 226.84f, 217.84f, 222.9f, 229.41f))
        candlesStickEntry.add(CandleEntry(3f, 222.0f, 216.12f, 214.94f, 216.41f))
        candlesStickEntry.add(CandleEntry(4f, 226.56f, 212.84f, 224.33f, 229.41f))
        candlesStickEntry.add(CandleEntry(5f, 221.12f, 269.84f, 228.14f, 216.41f))
        candlesStickEntry.add(CandleEntry(6f, 220.9f, 237.84f, 224.94f, 276.41f))

        val candleDataSet = CandleDataSet(candlesStickEntry, "first")
        candleDataSet.color = Color.rgb(80, 80,80)
        candleDataSet.shadowColor = ContextCompat.getColor(requireContext(),
            android.R.color.holo_green_light
        )
        candleDataSet.shadowWidth = 1f
        candleDataSet.decreasingColor = ContextCompat.getColor(requireContext(),android.R.color.holo_red_dark)
        candleDataSet.decreasingPaintStyle = Paint.Style.FILL


        candleDataSet.increasingColor = ContextCompat.getColor(requireContext(),
            android.R.color.holo_green_light
        )
        candleDataSet.increasingPaintStyle = Paint.Style.FILL

        val candledata = CandleData(candleDataSet)
        binding.candlechart.data = candledata
        binding.candlechart.setBackgroundColor(ContextCompat.getColor(requireContext(),android.R.color.white))
        binding.candlechart.animateXY(3000,3000)

        val xval = binding.candlechart.xAxis
        xval.position= XAxis.XAxisPosition.BOTTOM
        xval.setDrawGridLines(false)
    }


}


