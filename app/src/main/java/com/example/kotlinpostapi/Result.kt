package com.example.kotlinpostapi

data class Result<out T>(
    var resultType: ResultType,
    val data: T? = null,
    val error: Exception? = null,
    val errorMassage: String? = null
) {

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(ResultType.SUCCESS, data)
        }

        fun <T> error(error: Exception? = null, errorMessage: String): Result<T> {
            return Result(ResultType.ERROR, error = error, errorMassage = errorMessage)
        }
    }
}

enum class ResultType{
    SUCCESS,
    ERROR
}