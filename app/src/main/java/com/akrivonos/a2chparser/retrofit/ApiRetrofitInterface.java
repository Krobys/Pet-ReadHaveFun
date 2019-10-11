package com.akrivonos.a2chparser.retrofit;

import com.akrivonos.a2chparser.pojomodels.DvachModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface ApiRetrofitInterface {

    @GET("{nameBoard}/threads.json")
    Call<DvachModel> getThreads(@Path("nameBoard") String nameBoard);

}
