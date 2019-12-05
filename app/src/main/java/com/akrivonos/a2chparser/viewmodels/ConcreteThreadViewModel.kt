package com.akrivonos.a2chparser.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.akrivonos.a2chparser.base.BaseViewModel
import com.akrivonos.a2chparser.interfaces.FilteredItem
import com.akrivonos.a2chparser.retrofit.ApiRetrofitInterface
import com.akrivonos.a2chparser.utils.DFilterItems
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ConcreteThreadViewModel @Inject constructor(private var retrofit: ApiRetrofitInterface,
                                                  private var context: Context,
                                                  private val filter: DFilterItems,
                                                  private val sharedPreferenceUtils: SharedPreferenceUtils) : BaseViewModel() {
    private var postsList: List<FilteredItem> = ArrayList()
    private val mutableLiveData: MutableLiveData<List<FilteredItem>> = MutableLiveData()

    fun getPostsLiveData(nameBoard: String, numberThread: String): MutableLiveData<List<FilteredItem>> {
        if (postsList.isNotEmpty()) {
            postValue(postsList)
        } else {
            disposable += retrofit.getPostsForThread(nameBoard, numberThread)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onSuccess = { postModel ->
                        postModel.threads?.get(0)?.posts?.let {
                            postValue(it)
                        }
                    })
        }
        return mutableLiveData
    }

    private fun postValue(postList: List<FilteredItem>) {
        if (sharedPreferenceUtils.isFilterEnable(context)) {
            val consumer = Consumer<List<FilteredItem>> { t -> mutableLiveData.value = t }
            filter.filter(postList, consumer)
        } else {
            mutableLiveData.value = postList
        }
    }
}