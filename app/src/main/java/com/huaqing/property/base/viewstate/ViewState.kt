package com.huaqing.property.base.viewstate

sealed class ViewState<out T> : IViewState {

    companion object {
        fun <T> result(result: T): ViewState<T> = Result(result)
        fun <T> idle(): ViewState<T> = Idle
        fun <T> loading(): ViewState<T> = Refreshing
        fun <T> error(error: Throwable): ViewState<T> = Error(error)
    }

    object Idle : ViewState<Nothing>()
    object Refreshing : ViewState<Nothing>()
    data class Error(val error: Throwable) : ViewState<Nothing>()
    data class Result<out T>(val result: T) : ViewState<T>()
}