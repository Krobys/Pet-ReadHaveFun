package com.akrivonos.a2chparser.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.pojomodel.threadmodel.ThreadsModel
import com.akrivonos.a2chparser.retrofit.RetrofitSearchDvach
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ConcreteBoardViewModel(application: Application) : AndroidViewModel(application) {
    private var threadsList: List<Thread> = ArrayList()
    private var mutableLiveData: MutableLiveData<List<Thread>> = MutableLiveData()
    private val context = getApplication<Application>().applicationContext

    fun getThreadsForBoard(boardId: String?): MutableLiveData<List<Thread>> {
        if (threadsList.isNotEmpty()) {
            mutableLiveData.value = threadsList
        } else {
            val observerSuccess = object : Observer<ThreadsModel?> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(threadsModel: ThreadsModel) {
                    threadsModel.let {
                        SharedPreferenceUtils.setLastBoard(it.board, context)
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
            RetrofitSearchDvach.getThreadsForBoard(boardId, observerSuccess, observerError)
        }
        return mutableLiveData
    }

}