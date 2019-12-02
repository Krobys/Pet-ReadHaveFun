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
            retrofit.getPostsForThread(nameBoard, numberThread)
                    .subscribeOn(Schedulers.io())
                    .subscribeBy(onSuccess = {postModel ->
                        postModel.threads?.get(0)?.posts?.let{
                            postValue(it)
                        }
                    }, onError = {
                        Timber.d(it)
                        messageEvent.postValue(it.message)
                    })
        }
        return mutableLiveData
    }

    private fun postValue(postList: List<FilteredItem>){
        if(sharedPreferenceUtils.isFilterEnable(context)){
            val consumer = Consumer<List<FilteredItem>> { t -> mutableLiveData.value = t }
            filter.filter(postList, consumer)
        }else{
            mutableLiveData.value = postList
        }
    }
}