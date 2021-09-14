package bisq.android.chart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bisq.local.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import network.bisq.base.BaseViewModel

internal class ChartsViewModel(
    private val getChartsUseCase: GetChartsUseCase,
) : BaseViewModel() {

    internal val candles = MutableLiveData<Resource<List<CandleResponse>>>()
    fun getCharts(market: String, interval: CandleInterval) {
        viewModelScope.launch {
            getChartsUseCase.getCandles(market, interval)
                .onStart {
                    candles.postValue(Resource.loading())
                }.catch {
                    candles.postValue(Resource.error(it))
                }
                .collect {
                    candles.postValue(Resource.success(it))
                }
        }
    }

}


