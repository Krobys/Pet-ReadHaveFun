package com.akrivonos.a2chparser.retrofit

import android.util.Log
import com.akrivonos.a2chparser.builders.SubjectBuilder
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
import javax.inject.Inject


class RetrofitSearch @Inject constructor() {
    private val retrofit = Retrofit.Builder()
            .baseUrl(Companion.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    private val apiService: ApiRetrofitInterface = retrofit.create(ApiRetrofitInterface::class.java)

    fun getBoards(observerBoardThemes: io.reactivex.Observer<BoardModel>): RetrofitSearch? {
        val boardsPublishSubject = SubjectBuilder.createPublishSubject(observerBoardThemes)

        val beerModelCall = apiService.boards
        beerModelCall.enqueue(object : Callback<BoardModel> {
            override fun onResponse(call: Call<BoardModel>, response: Response<BoardModel>) {

                val boardModel = response.body()
                if (boardModel != null) {
                    if (boardsPublishSubject.hasObservers())
                        if (response.code() == 200) {
                            boardsPublishSubject.onNext(boardModel)
                        } else {
                            boardsPublishSubject.onNext(BoardModel())
                        }
                }
                boardsPublishSubject.onComplete()
            }

            override fun onFailure(call: Call<BoardModel>, t: Throwable) {
                Log.d(Companion.RETROFIT_MESSAGE_TAG, "error message: ${t.message}:")
                if (boardsPublishSubject.hasObservers())
                    boardsPublishSubject.onNext(BoardModel())
            }

        })
        return this
    }

    fun getThreadsForBoard(nameBoard: String, observerThreadsSuccess: io.reactivex.Observer<ThreadsModel?>, observerThreadsError: io.reactivex.Observer<List<Thread>?>): RetrofitSearch? {
        val threadsPublishSubjectSuccess = SubjectBuilder.createPublishSubject(observerThreadsSuccess)
        val threadsPublishSubjectError = SubjectBuilder.createPublishSubject(observerThreadsError)
        val modelCall = apiService.getThreadsForBoard(nameBoard)
        modelCall.enqueue(object : Callback<ThreadsModel> {
            override fun onResponse(call: Call<ThreadsModel>, response: Response<ThreadsModel>) {
                val threadsModel = response.body()
                if (threadsPublishSubjectSuccess.hasObservers() && threadsPublishSubjectError.hasObservers())
                        if (response.code() == 200) {
                            threadsModel?.let {
                                threadsPublishSubjectSuccess.onNext(it)
                            }
                        } else {
                            threadsPublishSubjectError.onNext(ArrayList())
                        }
                threadsPublishSubjectSuccess.onComplete()
            }

            override fun onFailure(call: Call<ThreadsModel>, t: Throwable) {
                Log.d(Companion.RETROFIT_MESSAGE_TAG, "error message: ${t.message}:")
                if (threadsPublishSubjectError.hasObservers())
                    threadsPublishSubjectError.onNext(ArrayList())
            }

        })
        return this
    }

    fun getPostsForThread(nameBoard: String, numberThread: String, observerPosts: io.reactivex.Observer<List<Post>>): RetrofitSearch? {
        val threadsPublishSubject = SubjectBuilder.createPublishSubject(observerPosts)

        val modelCall = apiService.getPostsForThread(nameBoard, numberThread)
        modelCall.enqueue(object : Callback<PostModel> {
            override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                val model = response.body()
                if (threadsPublishSubject.hasObservers())
                    if (response.code() == 200) {
                        model?.threads?.get(0)?.posts?.let { threadsPublishSubject.onNext(it) }
                    } else {
                        Log.d(Companion.RETROFIT_MESSAGE_TAG, "message: $response")
                        threadsPublishSubject.onNext(ArrayList())
                    }
                threadsPublishSubject.onComplete()
            }

            override fun onFailure(call: Call<PostModel>, t: Throwable) {
                Log.d(Companion.RETROFIT_MESSAGE_TAG, "error message: ${t.message}:")
                if (threadsPublishSubject.hasObservers())
                    threadsPublishSubject.onNext(ArrayList())
            }

        })
        return this
    }

    companion object {
        private const val BASE_URL = "https://2ch.hk/"
        private const val RETROFIT_MESSAGE_TAG = "RetrofitMessageTag"
    }


}
