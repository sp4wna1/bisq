package bisq.markets.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bisq.local.Currency
import bisq.local.Resource
import bisq.markets.usecase.CurrenciesUseCase
import br.com.elitma.remote.MarketsType
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import network.bisq.base.BaseViewModel

internal class CurrencyViewModel(
    private val currenciesUseCase: CurrenciesUseCase,
) : BaseViewModel() {

    internal val currencies = MutableLiveData<Resource<List<Currency>>>()

    fun fetchCurrencies() {
        viewModelScope.launch {
            currenciesUseCase.fetchCoins(MarketsType.FIAT.name).onStart {
                currencies.postValue(Resource.loading())
            }.catch {
                currencies.postValue(Resource.error(it))
            }.collect {
                currencies.postValue(Resource.success(it))
            }
        }
    }
}
