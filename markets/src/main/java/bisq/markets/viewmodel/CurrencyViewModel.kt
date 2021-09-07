package bisq.markets.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bisq.local.Currency
import bisq.local.Resource
import bisq.markets.usecase.CurrenciesUseCase
import bisq.markets.usecase.presenter.Presenter
import kotlinx.coroutines.launch
import network.bisq.base.BaseViewModel

internal class CurrencyViewModel(
    private val currenciesUseCase: CurrenciesUseCase,

    ) : BaseViewModel() {

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
}
