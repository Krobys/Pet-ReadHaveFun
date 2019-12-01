package com.akrivonos.a2chparser.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akrivonos.a2chparser.interfaces.FilteredItem
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.pojomodel.threadmodel.ThreadsModel
import com.akrivonos.a2chparser.retrofit.RetrofitSearch
import com.akrivonos.a2chparser.utils.DFilterItems
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import javax.inject.Inject

class ConcreteBoardViewModel@Inject constructor(private var retrofit: RetrofitSearch,
                                                private var context: Context,
                                                private val filter: DFilterItems,
                                                private val sharedPreferenceUtils: SharedPreferenceUtils) : ViewModel() {
    private var threadsList: List<FilteredItem> = ArrayList()
    private var mutableLiveData: MutableLiveData<List<FilteredItem>?> = MutableLiveData()

    private val observerSuccess = object : Observer<ThreadsModel?> {
        override fun onSubscribe(d: Disposable) {
        }

        override fun onNext(threadsModel: ThreadsModel) {
            threadsModel.let {
                it.threadsForBoard?.let { threads ->
                    threadsList = threads
                    postValue(threads)
                }
            }
        }

        override fun onError(e: Throwable) {

        }

        override fun onComplete() {
        }
    }
    private val observerError = object : Observer<List<Thread>?> {
        override fun onSubscribe(d: Disposable) {
        }

        override fun onNext(threads: List<Thread>) {
            mutableLiveData.value = null
        }

        override fun onError(e: Throwable) {

        }

        override fun onComplete() {
        }
    }

    fun getThreadsForBoard(boardId: String): MutableLiveData<List<FilteredItem>?> {
        if (threadsList.isNotEmpty()) {
            postValue(threadsList)
        } else {
            retrofit.getThreadsForBoard(boardId, observerSuccess, observerError)
        }
        return mutableLiveData
    }

    private fun postValue(threadList: List<FilteredItem>){
        if(sharedPreferenceUtils.isFilterEnable(context)){
            val consumer = Consumer<List<FilteredItem>> { t -> mutableLiveData.value = t }
            filter.filter(threadList, consumer)
        }else{
            mutableLiveData.value = threadList
        }
    }
}