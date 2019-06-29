package me.vinitagrawal.common.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel<out S : UIState> : ViewModel() {
    abstract fun getData(): LiveData<out S>
    abstract fun onRender()

    private val compositeDisposable by lazy { CompositeDisposable() }

    fun Disposable.autoDispose() {
        compositeDisposable.add(this)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}

interface UIState