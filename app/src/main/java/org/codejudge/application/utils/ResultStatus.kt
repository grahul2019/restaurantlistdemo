package org.codejudge.application.utils

data class ResultStatus<out T>(val status: Status, val data: T?=null, val message: String?=null) {
    companion object {
        fun <T> success(data: T): ResultStatus<T> = ResultStatus(status = Status.SUCCESS, data = data)

        fun <T> error(data: T?=null, message: String): ResultStatus<T> = ResultStatus(status = Status.ERROR, message = message)

        fun <T> loading(data: T?): ResultStatus<T> = ResultStatus(status = Status.LOADING)

        fun <T> empty (data:T?): ResultStatus<T> = ResultStatus(status = Status.EMPTY,message = null)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    EMPTY
}