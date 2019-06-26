package me.vinitagrawal.common.utils

import android.util.Log

class Logger {

    fun d(tag: String, message: String?) {
        message?.run { Log.d(tag, this) }
    }

    fun i(tag: String, message: String?) {
        message?.run { Log.d(tag, this) }
    }

    fun e(tag: String, message: String?) {
        message?.run { Log.d(tag, this) }
    }

    fun logException(throwable: Throwable) {
        throwable.printStackTrace()
    }
}