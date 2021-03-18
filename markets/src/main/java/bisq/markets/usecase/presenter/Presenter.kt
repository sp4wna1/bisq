package bisq.markets.usecase.presenter

interface Presenter<T> {
    fun loading()
    fun error()
    fun success(result: T)
}