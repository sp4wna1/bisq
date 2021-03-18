package bisq.markets.usecase

import bisq.local.BidsAndAsks
import bisq.markets.usecase.presenter.Presenter

interface DepthUseCase {
    suspend fun fetchDepth(market: String, presenter: Presenter<BidsAndAsks>)
}