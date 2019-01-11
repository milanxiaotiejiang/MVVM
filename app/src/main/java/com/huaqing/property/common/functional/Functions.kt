package com.huaqing.property.common.functional

typealias Supplier<T> = () -> T

interface Consumer<T> {

    fun accept(t: T)
}