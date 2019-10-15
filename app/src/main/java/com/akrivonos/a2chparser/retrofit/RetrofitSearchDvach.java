package com.akrivonos.a2chparser.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardModel;
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread;
import com.akrivonos.a2chparser.pojomodel.threadmodel.ThreadsModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@SuppressWarnings("UnusedReturnValue")
public class RetrofitSearchDvach {

    private BehaviorSubject<List<Thread>> threadsBehaviorSubject;
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

    public RetrofitSearchDvach getThreadsForBoard(String nameBoard) {

        Call<ThreadsModel> beerModelCall = apiService.getThreads(nameBoard);
        beerModelCall.enqueue(new Callback<ThreadsModel>() {
            @Override
            public void onResponse(@NonNull Call<ThreadsModel> call, @NonNull Response<ThreadsModel> response) {
                Log.d(TAG, "onResponse: "+response.toString());
                ThreadsModel dvachModel = response.body();
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
            public void onFailure(@NonNull Call<ThreadsModel> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: ");
                if (threadsBehaviorSubject.hasObservers())
                    threadsBehaviorSubject.onNext(new ArrayList<Thread>());
            }

        });
        return retrofitSearchDownload;
    }

    public RetrofitSearchDvach getBoardsAsync(final io.reactivex.Observer<BoardModel> observerBoardThemes) {
        final PublishSubject<BoardModel> threadsPublishSubject = PublishSubject.create();
        threadsPublishSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observerBoardThemes);

        Call<BoardModel> beerModelCall = apiService.getBoardsList();
        beerModelCall.enqueue(new Callback<BoardModel>() {
            @Override
            public void onResponse(@NonNull Call<BoardModel> call, @NonNull Response<BoardModel> response) {

                Log.d(TAG, "onResponse: "+response.toString());
                BoardModel boardModel = response.body();
                    if (boardModel != null) {
                        if (threadsPublishSubject.hasObservers())
                            if (response.code() == 200) {
                                Log.d(TAG, "onResponse: +");
                               threadsPublishSubject.onNext(boardModel);
                            } else {
                               threadsPublishSubject.onNext(new BoardModel());
                            }
                    }else{
                        Log.d(TAG, "onResponse: boardmodel null");
                    }
            }

            @Override
            public void onFailure(@NonNull Call<BoardModel> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: ");
                if (threadsPublishSubject.hasObservers())
                    threadsPublishSubject.onNext(new BoardModel());
            }

        });
        return retrofitSearchDownload;
    }
}
