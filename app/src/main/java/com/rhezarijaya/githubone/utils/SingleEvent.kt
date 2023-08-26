package com.rhezarijaya.githubone.utils

open class SingleEvent<out T>(private val data: T) {
    @Suppress("MemberVisibilityCanBePrivate")
    var hasRetrieved = false
        private set

    fun getData(): T? = if (hasRetrieved) {
        null
    } else {
        hasRetrieved = true
        data
    }

    fun peekData(): T = data
}