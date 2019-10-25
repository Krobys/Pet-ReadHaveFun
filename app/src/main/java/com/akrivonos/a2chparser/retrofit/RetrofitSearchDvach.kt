package com.akrivonos.a2chparser.retrofit

import android.util.Log
import com.akrivonos.a2chparser.fabrics.SubjectFactory
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardModel
import com.akrivonos.a2chparser.pojomodel.postmodel.Post
import com.akrivonos.a2chparser.pojomodel.postmodel.PostModel
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.pojomodel.threadmodel.ThreadsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


object RetrofitSearchDvach {
    private val BASE_URL = "https://2ch.hk/"
    private val TAG = "test"
    val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    private val apiService: ApiRetrofitInterface = retrofit.create(ApiRetrofitInterface::class.java)


    fun getBoards(observerBoardThemes: io.reactivex.Observer<BoardModel>): RetrofitSearchDvach? {
        val boardsPublishSubject = SubjectFactory.createPublishSubject(observerBoardThemes)

        val beerModelCall = apiService.boards
        beerModelCall.enqueue(object : Callback<BoardModel> {
            override fun onResponse(call: Call<BoardModel>, response: Response<BoardModel>) {

                Log.d(TAG, "onResponse: $response")
                val boardModel = response.body()
                if (boardModel != null) {
                    if (boardsPublishSubject.hasObservers())
                        if (response.code() == 200) {
                            Log.d(TAG, "onResponse: +")
                            boardsPublishSubject.onNext(boardModel)
                        } else {
                            boardsPublishSubject.onNext(BoardModel())
                        }
                }
                boardsPublishSubject.onComplete()
            }

            override fun onFailure(call: Call<BoardModel>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                if (boardsPublishSubject.hasObservers())
                    boardsPublishSubject.onNext(BoardModel())
            }

        })
        return this
    }

    fun getThreadsForBoardForBoard(nameBoard: String?, observerThreads: io.reactivex.Observer<List<Thread>?>): RetrofitSearchDvach? {
        val threadsPublishSubject = SubjectFactory.createPublishSubject(observerThreads)

        val modelCall = apiService.getThreadsForBoard(nameBoard)
        modelCall.enqueue(object : Callback<ThreadsModel> {
            override fun onResponse(call: Call<ThreadsModel>, response: Response<ThreadsModel>) {
                Log.d(TAG, "onResponse: $response")
                val threadsModel = response.body()
                if (threadsPublishSubject.hasObservers())
                    if (threadsModel != null) {
                        if (response.code() == 200) {
                            threadsModel.threadsForBoard?.let {
                                threadsPublishSubject.onNext(it)
                            }
                        } else {
                            threadsPublishSubject.onNext(ArrayList())
                        }
                    }
                threadsPublishSubject.onComplete()
            }

            override fun onFailure(call: Call<ThreadsModel>, t: Throwable) {
                if (threadsPublishSubject.hasObservers())
                    threadsPublishSubject.onNext(ArrayList())
            }

        })
        return this
    }

    fun getPostsForThread(nameBoard: String?, numberThread: String?, observerThreads: io.reactivex.Observer<List<Post>>): RetrofitSearchDvach? {
        val threadsPublishSubject = SubjectFactory.createPublishSubject(observerThreads)

        val modelCall = apiService.getPostsForThread(nameBoard, numberThread)
        modelCall.enqueue(object : Callback<PostModel> {
            override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                Log.d(TAG, "onResponse: $response")
                val model = response.body()
                if (threadsPublishSubject.hasObservers())
                    if (response.code() == 200) {
                        Log.d(TAG, "onResponse: +")
                        model?.threads?.get(0)?.posts?.let { threadsPublishSubject.onNext(it) }
                    } else {
                        threadsPublishSubject.onNext(ArrayList())
                    }
                threadsPublishSubject.onComplete()
            }

            override fun onFailure(call: Call<PostModel>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                if (threadsPublishSubject.hasObservers())
                    threadsPublishSubject.onNext(ArrayList())
            }

        })
        return this
    }

}
