package com.alkss.meight.feature_delivery.presentation.util

fun <T> List<T>.getNext(element: T): T? {
    val index = this.indexOf(element)
    return if (index in 0 until this.lastIndex) this[index + 1] else null
}