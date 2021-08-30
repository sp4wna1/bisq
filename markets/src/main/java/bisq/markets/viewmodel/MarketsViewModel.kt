package bisq.markets.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bisq.local.Currency
import bisq.local.Offer
import bisq.local.Resource
import bisq.markets.usecase.CurrenciesUseCase
import bisq.markets.usecase.OffersUseCase
import bisq.markets.usecase.presenter.Presenter
import kotlinx.coroutines.launch
import network.bisq.base.BaseViewModel

internal class MarketsViewModel(
    private val offersUseCase: OffersUseCase,
    private val currenciesUseCase: CurrenciesUseCase
) : BaseViewModel() {

    internal val offers = MutableLiveData<Resource<Pair<List<Offer>, List<Offer>>>>()
    internal val currencies = MutableLiveData<Resource<List<Currency>>>()

    init {
        fetchCurrencies()
    }

    private fun fetchCurrencies() {
        viewModelScope.launch {
            currenciesUseCase.fetchCoins(object : Presenter<List<Currency>> {
                override fun loading() {
                    currencies.postValue(Resource.loading())
                }

                override fun error(throwable: Throwable?) {
                    currencies.postValue(Resource.error(throwable))
                }

                override fun success(result: List<Currency>) {
                    currencies.postValue(Resource.success(result))
                }

            })
        }
    }

    fun fetchOffers(market: String) {
        viewModelScope.launch {
            offersUseCase.fetchOffers(market, object : Presenter<Pair<List<Offer>, List<Offer>>> {
                override fun loading() {
                    offers.postValue(Resource.loading())
                }

                override fun error(throwable: Throwable?) {
                    offers.postValue(Resource.error(throwable))
                }

                override fun success(result: Pair<List<Offer>, List<Offer>>) {
                    offers.postValue(Resource.success(result))
                }
            })
        }
    }
}