package bisq.markets.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bisq.local.Offer
import bisq.local.Resource
import bisq.markets.usecase.OffersUseCase
import bisq.markets.usecase.presenter.Presenter
import kotlinx.coroutines.launch
import network.bisq.base.BaseViewModel

internal class MarketsViewModel(
    private val offersUseCase: OffersUseCase
) : BaseViewModel() {

    internal val offers = MutableLiveData<Resource<Pair<List<Offer>, List<Offer>>>>()

    fun fetchOffers(market: String) {
        viewModelScope.launch {
            offersUseCase.fetchOffers(market, object : Presenter<Pair<List<Offer>, List<Offer>>> {
                override fun loading() {
                    offers.postValue(Resource.loading())
                }

                override fun error() {
                    offers.postValue(Resource.error())
                }

                override fun success(result: Pair<List<Offer>, List<Offer>>) {
                    offers.postValue(Resource.success(result))
                }
            })
        }
    }
}