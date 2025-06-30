package com.iagoaf.appfinancas.core.result

sealed class BaseResult<T> {
    data class Success<T>(val data: T) : BaseResult<T>()
    data class Error<T>(val message: String) : BaseResult<T>()

    inline fun onSuccess(action: (data: T) -> Unit): BaseResult<T> {
        if (this is Success) action(data)
        return this
    }

    inline fun onError(action: (String) -> Unit): BaseResult<T> {
        if (this is Error) action(message)
        return this
    }
}