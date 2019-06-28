package me.vinitagrawal.common.utils

import io.reactivex.Observable

fun <T> Observable<T>.doOnEvent(function: () -> Unit) =
    doOnNext { function.invoke() }
        .doOnError { function.invoke() }!!