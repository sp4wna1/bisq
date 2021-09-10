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
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

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
        var x = 0F
        binding.buttonAddCandle.setOnClickListener {
            addCandle(x)
            x += 1

        }


    }


    fun addCandle(x: Float) {
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

        binding.candlechart.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                android.R.color.white
            )
        )
        binding.candlechart.animateXY(1000, 1000)

        val xval = binding.candlechart.xAxis
        xval.position = XAxis.XAxisPosition.BOTTOM
        xval.setDrawGridLines(false)


        val candleEntry = CandleEntry(
            x,
            Random.nextDouble(0.0, 100.0).toFloat(),
            Random.nextDouble(0.0, 100.0).toFloat(),
            Random.nextDouble(0.0, 100.0).toFloat(),
            Random.nextDouble(0.0, 100.0).toFloat()
        )
        val candleList = ArrayList<CandleEntry>()
        candleList.add(candleEntry)

        val candleDataSet = CandleDataSet(candleList, "first")
        candleDataSet.color = Color.rgb(80, 80, 80)
        candleDataSet.shadowColor = ContextCompat.getColor(
            requireContext(),
            android.R.color.holo_green_light
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


        val data = binding.candlechart.data
        if (data == null) {
            binding.candlechart.data = CandleData(candleDataSet)
        } else {
            binding.candlechart.data.addEntry(
                candleEntry,
                (Math.random() * data.dataSetCount).toInt()
            )
            data.notifyDataChanged()
            binding.candlechart.notifyDataSetChanged()
        }
    }


}

