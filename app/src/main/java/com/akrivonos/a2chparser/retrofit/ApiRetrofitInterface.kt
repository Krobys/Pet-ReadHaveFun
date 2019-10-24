package com.akrivonos.a2chparser.retrofit

import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardModel
import com.akrivonos.a2chparser.pojomodel.postmodel.PostModel
import com.akrivonos.a2chparser.pojomodel.threadmodel.ThreadsModel

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ApiRetrofitInterface {

    @get:GET("makaba/mobile.fcgi?task=get_boards")
    val boards: Call<BoardModel>

    @GET("{nameBoard}/catalog.json")
    fun getThreadsForBoard(@Path("nameBoard") nameBoard: String?): Call<ThreadsModel>

    @GET
    fun getPostsForThread(@Path("nameBoard") nameBoard: String?, @Path("numThread") numThread: String?): Call<PostModel>
}
