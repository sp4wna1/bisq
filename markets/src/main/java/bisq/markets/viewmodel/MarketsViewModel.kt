package bisq.markets.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bisq.local.BidsAndAsks
import bisq.local.Resource
import bisq.markets.usecase.DepthUseCase
import bisq.markets.usecase.presenter.Presenter
import kotlinx.coroutines.launch
import network.bisq.base.BaseViewModel

internal class MarketsViewModel(private val depthUseCase: DepthUseCase) : BaseViewModel() {

    internal val depth = MutableLiveData<Resource<BidsAndAsks>>()

    fun fetchDepth(market: String) {
        viewModelScope.launch {
            depthUseCase.fetchDepth(market, object :
                Presenter<BidsAndAsks> {
                override fun loading() {
                    depth.postValue(Resource.loading())
                }

                override fun error() {
                    depth.postValue(Resource.error())
                }

                override fun success(result: BidsAndAsks) {
                    depth.postValue(Resource.success(result))
                }
            })
        }
    }
}