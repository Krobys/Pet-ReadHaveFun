package com.akrivonos.a2chparser.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    protected val disposable = CompositeDisposable()
    val messageEvent = MutableLiveData<String>()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}