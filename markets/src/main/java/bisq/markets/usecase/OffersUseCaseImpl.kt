package bisq.markets.usecase

import bisq.local.Offer
import bisq.markets.repository.OffersRepository
import bisq.markets.usecase.presenter.Presenter
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import java.math.BigDecimal
import java.util.*

internal class OffersUseCaseImpl(private val offersRepository: OffersRepository) : OffersUseCase {

    override suspend fun fetchOffers(
        market: String,
        presenter: Presenter<Pair<List<Offer>, List<Offer>>>
    ) {
        offersRepository.fetchOffers(market)
            .onStart {
                presenter.loading()
            }
            .catch {
                presenter.error()
            }
            .collect {
                val buys = it.buysAndSellsResponse?.buys
                val sells = it.buysAndSellsResponse?.sells
                if (buys != null && sells != null) {
                    presenter.success(
                        Pair(
                            buys.map {
                                Offer(
                                    it.direction ?: "",
                                    Date(it.offerDate ?: Calendar.getInstance().timeInMillis),
                                    it.price ?: BigDecimal.ZERO,
                                    it.volume ?: BigDecimal.ZERO
                                )
                            },
                            sells.map {
                                Offer(
                                    it.direction ?: "",
                                    Date(it.offerDate ?: Calendar.getInstance().timeInMillis),
                                    it.price ?: BigDecimal.ZERO,
                                    it.volume ?: BigDecimal.ZERO
                                )
                            }
                        )
                    )
                } else {
                    presenter.error()
                }
            }
    }
}