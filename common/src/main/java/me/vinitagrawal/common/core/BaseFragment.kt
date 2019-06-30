package me.vinitagrawal.common.core

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel>(private val viewModelClass: Class<VM>) : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: VM

    abstract fun observeData()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
        observeData()
    }

    fun <T> LiveData<T>.observe(onStateChanged: (T) -> Unit) {
        this.observe(viewLifecycleOwner, Observer { onStateChanged.invoke(it) })
        viewModel.onRender()
    }

    fun <T : Fragment> addFragment(@IdRes id: Int, fragment: T) {
        activity?.let {
            it.supportFragmentManager
                    .beginTransaction()
                    .add(id, fragment, fragment.tag)
                    .addToBackStack(fragment.tag)
                    .commit()
        }
    }
}