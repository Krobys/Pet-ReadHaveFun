package com.akrivonos.a2chparser.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.akrivonos.a2chparser.BR
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel, B:ViewDataBinding> : BaseFragmentWithoutViewModel<B>() {
    protected abstract val viewModelClass: Class<VM>

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory
    protected val viewModel: VM by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
        return binding.root
    }

}