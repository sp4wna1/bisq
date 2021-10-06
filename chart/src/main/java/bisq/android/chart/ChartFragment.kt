package bisq.android.chart

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import bisq.android.chart.databinding.FragmentChartBinding
import bisq.local.Resource
import br.com.elitma.remote.remoteModule
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.tabs.TabLayout
import network.bisq.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class ChartFragment : BaseFragment() {

    private lateinit var binding: FragmentChartBinding
    private val viewModel: ChartsViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentChartBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadKoinModules(listOf(remoteModule, chartModule, tickerModule, volumeMarketModule))
    }

    override fun onDetach() {
        super.onDetach()
        unloadKoinModules(listOf(remoteModule, chartModule, tickerModule, volumeMarketModule))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCharts(requireArguments().getString("pair")?:"", CandleInterval.DAY)
        viewModel.getVolumes(requireArguments().getString("pair")?:"", CandleInterval.DAY)

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
                    binding.progressChart.visibility = View.INVISIBLE

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

        val newFragment = CandleFragment().apply {
            arguments = Bundle().apply {
                putString("pair", "btc_brl")
            }
        }
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view_charts, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()

        LineFragment().apply {
            arguments = Bundle().apply {
                putString("pair", "btc_brl")
            }
        }
    }








        private fun configTabLayout() {

            binding.chartsTabLayout.addTab(binding.chartsTabLayout.newTab().apply {
                text = "CandlesChart"

            })
            binding.chartsTabLayout.addTab(binding.chartsTabLayout.newTab().setText("LinesChart"))

            binding.chartsTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
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





