package com.akrivonos.a2chparser.retrofit

import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardModel
import com.akrivonos.a2chparser.pojomodel.postmodel.PostModel
import com.akrivonos.a2chparser.pojomodel.threadmodel.ThreadsModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRetrofitInterface {

    @GET("makaba/mobile.fcgi?task=get_boards")
    fun getBoards(): Single<BoardModel>

    @GET("{nameBoard}/catalog.json")
    fun getThreadsForBoard(@Path("nameBoard") nameBoard: String?): Single<ThreadsModel>

    @GET("{nameBoard}/res/{numThread}.json")
    fun getPostsForThread(@Path("nameBoard") nameBoard: String?, @Path("numThread") numThread: String?): Single<PostModel>
}
