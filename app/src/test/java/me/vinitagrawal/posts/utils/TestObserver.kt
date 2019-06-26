package me.vinitagrawal.posts.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class TestObserver<T> : Observer<T> {

    private val values = mutableListOf<T>()

    override fun onChanged(value: T) {
        values.add(value)
    }

    fun assertValues(function: (List<T>) -> Unit) {
        function.invoke(values)
    }
}

fun <T> LiveData<T>.test() = TestObserver<T>().also {
    observeForever(it)
}