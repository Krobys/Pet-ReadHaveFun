package com.akrivonos.a2chparser.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akrivonos.a2chparser.dagger.components.DaggerDaggerComponent
import com.akrivonos.a2chparser.pojomodel.postmodel.Post
import com.akrivonos.a2chparser.retrofit.RetrofitSearch
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ConcreteThreadViewModel : ViewModel() {
    private var postsList: List<Post> = ArrayList()
    private val mutableLiveData: MutableLiveData<List<Post>> = MutableLiveData()
    @Inject
    lateinit var retrofit: RetrofitSearch

    init {
        DaggerDaggerComponent.create().inject(this)
    }
    fun getPostsLiveData(nameBoard: String, numberThread: String): MutableLiveData<List<Post>> {
        if (postsList.isNotEmpty()) {
            mutableLiveData.value = postsList
        } else {
            val observer = object : Observer<List<Post>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(postList: List<Post>) {
                    postsList = postList
                    mutableLiveData.value = postList
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

            }
            retrofit.getPostsForThread(nameBoard, numberThread, observer)
        }
        return mutableLiveData
    }
}