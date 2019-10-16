package com.akrivonos.a2chparser.retrofit;

import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardModel;
import com.akrivonos.a2chparser.pojomodel.threadmodel.ThreadsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface ApiRetrofitInterface {

    @GET("{nameBoard}/catalog.json")
    Call<ThreadsModel> getThreads(@Path("nameBoard") String nameBoard);

    @GET("makaba/mobile.fcgi?task=get_boards")
    Call<BoardModel> getBoardsList();
}
