package bisq.android.chart

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TableLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.green
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import bisq.android.chart.databinding.FragmentChartBinding
import bisq.local.Resource
import br.com.elitma.remote.remoteModule
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import network.bisq.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class ChartFragment : BaseFragment() {

    private lateinit var binding: FragmentChartBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadKoinModules(listOf(remoteModule, chartModule, tickerModule))
    }

    override fun onDetach() {
        super.onDetach()
        unloadKoinModules(listOf(remoteModule, chartModule, tickerModule))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentChartBinding.inflate(inflater, container, false).apply {
        binding = this

        val args  by navArgs<ChartFragmentArgs>()
        configTabLayout()
        binding.chartsViewPager2.adapter = FragmentAdapter(listOf(
            CandleFragment().apply {
          arguments = Bundle().apply {
                    putString("pair", "btc_brl")
                }
            },
            LineFragment().apply { arguments = Bundle().apply {
                    putString("pair", "btc_brl")
                }
            }
        ), childFragmentManager, lifecycle
        )
        configViewPager()


    }.root

    private fun configViewPager() {
        binding.chartsViewPager2.isUserInputEnabled = false
        binding.chartsViewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.chartsTabLayout.selectTab(binding.chartsTabLayout.getTabAt(position))
            }
        })
    }


    private fun configTabLayout() {
        binding.chartsTabLayout.addTab(binding.chartsTabLayout.newTab().apply {
            text = "CandlesChart"

        })
        binding.chartsTabLayout.addTab(binding.chartsTabLayout.newTab().setText("LinesChart"))

        binding.chartsTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) binding.chartsViewPager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}

