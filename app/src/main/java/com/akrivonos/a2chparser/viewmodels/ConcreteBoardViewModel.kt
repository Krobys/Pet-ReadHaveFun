package com.akrivonos.a2chparser.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.pojomodel.threadmodel.ThreadsModel
import com.akrivonos.a2chparser.retrofit.RetrofitSearch
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ConcreteBoardViewModel@Inject constructor(private var retrofit: RetrofitSearch, private var context: Context) : ViewModel() {
    private var threadsList: List<Thread> = ArrayList()
    private var mutableLiveData: MutableLiveData<List<Thread>> = MutableLiveData()

    fun getThreadsForBoard(boardId: String): MutableLiveData<List<Thread>> {
        if (threadsList.isNotEmpty()) {
            mutableLiveData.value = threadsList
        } else {
            val observerSuccess = object : Observer<ThreadsModel?> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(threadsModel: ThreadsModel) {
                    threadsModel.let {
                        it.threadsForBoard?.let { threads ->
                            threadsList = threads
                            mutableLiveData.value = threads
                        }
                    }
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                }
            }
            val observerError = object : Observer<List<Thread>?> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(threads: List<Thread>) {
                    mutableLiveData.value = threads
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                }
            }
            retrofit.getThreadsForBoard(boardId, observerSuccess, observerError)
        }
        return mutableLiveData
    }

}