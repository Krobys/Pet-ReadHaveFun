package com.akrivonos.a2chparser.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.akrivonos.a2chparser.interfaces.FilteredItem
import com.akrivonos.a2chparser.retrofit.ApiRetrofitInterface
import com.akrivonos.a2chparser.utils.DFilterItems
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
import com.rxchainretrier.base.BaseViewModel
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ConcreteBoardViewModel@Inject constructor(private var retrofit: ApiRetrofitInterface,
                                                private var context: Context,
                                                private val filter: DFilterItems,
                                                private val sharedPreferenceUtils: SharedPreferenceUtils) : BaseViewModel() {
    private var threadsList: List<FilteredItem> = ArrayList()
    private var mutableLiveData: MutableLiveData<List<FilteredItem>> = MutableLiveData()

    fun getThreadsForBoard(boardId: String): MutableLiveData<List<FilteredItem>> {
        if (threadsList.isNotEmpty()) {
            postValue(threadsList)
        } else {
            retrofit.getThreadsForBoard(boardId)
                    .subscribeOn(Schedulers.io())
                    .subscribeBy (onSuccess = {threadsModel->
                        threadsModel.let {
                            it.threadsForBoard?.let { threads ->
                                threadsList = threads
                                postValue(threads)
                            }
                        }
                    }, onError = {
                        Timber.d(it)
                        messageEvent.postValue(it.message)
                    })
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