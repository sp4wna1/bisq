package bisq.markets.usecase.presenter

interface Presenter<T> {
    fun loading()
    fun error(throwable: Throwable? = null)
    fun success(result: T)
}