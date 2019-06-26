package me.vinitagrawal.common.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<out S : UIState> : ViewModel() {
    abstract fun getData(): LiveData<out S>
    abstract fun onRender()
}

interface UIState