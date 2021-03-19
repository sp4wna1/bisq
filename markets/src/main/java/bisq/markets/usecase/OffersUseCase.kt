package bisq.markets.usecase

import bisq.local.Offer
import bisq.markets.usecase.presenter.Presenter

internal interface OffersUseCase {
    suspend fun fetchOffers(
        market: String,
        presenter: Presenter<Pair<List<Offer>, List<Offer>>>
    )
}