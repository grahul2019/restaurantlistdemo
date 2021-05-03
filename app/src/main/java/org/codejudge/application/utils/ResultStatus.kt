package org.codejudge.application.utils

data class ResultStatus<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): ResultStatus<T> = ResultStatus(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): ResultStatus<T> = ResultStatus(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): ResultStatus<T> = ResultStatus(status = Status.LOADING, data = data, message = null)

        fun <T> empty (data:T?): ResultStatus<T> = ResultStatus(status = Status.EMPTY,data = data,message = null)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    EMPTY
}