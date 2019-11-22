package com.akrivonos.a2chparser.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akrivonos.a2chparser.interfaces.FilteredItem
import com.akrivonos.a2chparser.pojomodel.postmodel.Post
import com.akrivonos.a2chparser.retrofit.RetrofitSearch
import com.akrivonos.a2chparser.utils.DFilterItems
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import javax.inject.Inject

class ConcreteThreadViewModel @Inject constructor(private var retrofit: RetrofitSearch,
                                                  private var context: Context,
                                                  private val filter: DFilterItems,
                                                  private val sharedPreferenceUtils: SharedPreferenceUtils) : ViewModel() {
    private var postsList: List<FilteredItem> = ArrayList()
    private val mutableLiveData: MutableLiveData<List<FilteredItem>> = MutableLiveData()

    fun getPostsLiveData(nameBoard: String, numberThread: String): MutableLiveData<List<FilteredItem>> {
        if (postsList.isNotEmpty()) {
            postValue(postsList)
        } else {
            val observer = object : Observer<List<Post>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(postList: List<Post>) {
                    postsList = postList
                    postValue(postList)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

            }
            retrofit.getPostsForThread(nameBoard, numberThread, observer)
        }
        return mutableLiveData
    }

    private fun postValue(postList: List<FilteredItem>){
        Log.d("test", "postValue:")
        if(sharedPreferenceUtils.isFilterEnable(context)){
            val consumer = Consumer<List<FilteredItem>> { t -> mutableLiveData.value = t
                Log.d("test", "post with filter:")}
            filter.filter(postList, consumer)
        }else{
            Log.d("test", "post without filter:")
            mutableLiveData.value = postList
        }
    }
}