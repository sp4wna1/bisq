package bisq.markets.usecase

import bisq.local.BidsAndAsks
import bisq.markets.repository.DepthRepository
import bisq.markets.usecase.presenter.Presenter
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

@InternalCoroutinesApi
class DepthUseCaseImpl(private val depthRepository: DepthRepository) :
    DepthUseCase {
    override suspend fun fetchDepth(market: String, presenter: Presenter<BidsAndAsks>) {
        depthRepository.fetchDepth(market)
            .onStart { presenter.loading() }
            .catch { presenter.error(it) }
            .collect { result ->
                if (result == null) {
                    presenter.error()
                } else {
                    presenter.success(
                        BidsAndAsks(
                            result.buys ?: emptyList(),
                            result.sells ?: emptyList()
                        )
                    )
                }
            }
    }
}