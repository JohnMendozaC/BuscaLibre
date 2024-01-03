package com.john.buscalibre.shared.ui.status

sealed interface StatusUI<T> {
    class Loading<T> : StatusUI<T>
    data class Error<T>(val errorCode: Int) : StatusUI<T>
    data class Success<T>(val result: T) : StatusUI<T>
}
