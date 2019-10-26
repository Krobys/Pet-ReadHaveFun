package com.akrivonos.a2chparser.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.retrofit.RetrofitSearchDvach
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ConcreteBoardViewModel : ViewModel() {
    private var threadsList: List<Thread> = ArrayList()
    private var mutableLiveData: MutableLiveData<List<Thread>> = MutableLiveData()

    fun getThreadsFoBoard(boardId: String?): MutableLiveData<List<Thread>> {
        if (threadsList.isNotEmpty()) {
            mutableLiveData.value = threadsList
        } else {
            val observer = object : Observer<List<Thread>?> {
                lateinit var disposable: Disposable
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(threads: List<Thread>) {
                    threadsList = threads
                    mutableLiveData.value = threads
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    disposable.dispose()
                }
            }
            RetrofitSearchDvach.getThreadsForBoardForBoard(boardId, observer)
        }
        return mutableLiveData
    }

}