package com.huaqing.property.model

sealed class Errors : Throwable() {
    object NetworkError : Errors()
    object EmptyInputError : Errors()
    object EmptyResultsError : Errors()
    object SingleError : Errors()
    data class Error(val code: Int, val errorMsg: String) : Errors()
}