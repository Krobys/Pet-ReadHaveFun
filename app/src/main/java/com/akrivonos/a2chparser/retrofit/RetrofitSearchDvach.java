package com.akrivonos.a2chparser.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.akrivonos.a2chparser.pojomodels.DvachModel;
import com.akrivonos.a2chparser.pojomodels.Thread;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@SuppressWarnings("UnusedReturnValue")
public class RetrofitSearchDvach {

    private Call lastProcess;
    private BehaviorSubject<List<Thread>> threadsBehaviorSubject;
    private final static String SANDBOX_API_KEY = "14bac69989f93ce2755e0830d3a5c851";
    private final static String BASE_URL = "https://2ch.hk/";
    private static RetrofitSearchDvach retrofitSearchDownload;
    private final ApiRetrofitInterface apiService;
    private String TAG = "test";
    private RetrofitSearchDvach() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiRetrofitInterface.class);
    }

    public static RetrofitSearchDvach getInstance() {
        if (retrofitSearchDownload == null) {
            retrofitSearchDownload = new RetrofitSearchDvach();
        }
        return retrofitSearchDownload;
    }

    public RetrofitSearchDvach setObserverBeerNames(io.reactivex.Observer<List<Thread>> observer) {
        threadsBehaviorSubject = BehaviorSubject.create();
        threadsBehaviorSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return retrofitSearchDownload;
    }


    public RetrofitSearchDvach getThreadsForBoard(String nameBoard) {

        Call<DvachModel> beerModelCall = apiService.getThreads(nameBoard);
        beerModelCall.enqueue(new Callback<DvachModel>() {
            @Override
            public void onResponse(@NonNull Call<DvachModel> call, @NonNull Response<DvachModel> response) {
                Log.d(TAG, "onResponse: "+response.toString());
                DvachModel dvachModel = response.body();
                if (threadsBehaviorSubject.hasObservers())
                    if (dvachModel != null) {
                        if (response.code() == 200) {
                            Log.d(TAG, "onResponse: +");
                            threadsBehaviorSubject.onNext(dvachModel.getThreads());
                        } else {
                            threadsBehaviorSubject.onNext(new ArrayList<Thread>());
                        }
                    }
            }

            @Override
            public void onFailure(@NonNull Call<DvachModel> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: ");
                if (threadsBehaviorSubject.hasObservers())
                    threadsBehaviorSubject.onNext(new ArrayList<Thread>());
            }

        });
        if (lastProcess != null) {
            if (!lastProcess.isExecuted())
                lastProcess.cancel();
        }
        lastProcess = beerModelCall;
        return retrofitSearchDownload;
    }

}
