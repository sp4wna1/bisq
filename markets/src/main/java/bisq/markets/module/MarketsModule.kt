package bisq.markets.module

import bisq.markets.viewmodel.MarketsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val marketsModule = module {
    viewModel { MarketsViewModel() }
}