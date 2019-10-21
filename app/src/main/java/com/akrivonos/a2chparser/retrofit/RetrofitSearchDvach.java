package com.akrivonos.a2chparser.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.akrivonos.a2chparser.fabrics.SubjectFactory;
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardModel;
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread;
import com.akrivonos.a2chparser.pojomodel.threadmodel.ThreadsModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@SuppressWarnings("UnusedReturnValue")
public class RetrofitSearchDvach {

    private final static String BASE_URL = "https://2ch.hk/";
    private static RetrofitSearchDvach retrofitSearchDownload;
    private final ApiRetrofitInterface apiService;
    private final String TAG = "test";

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

    public RetrofitSearchDvach getBoards(final io.reactivex.Observer<BoardModel> observerBoardThemes) {
        final PublishSubject<BoardModel> boardsPublishSubject = SubjectFactory.createPublishSubject(observerBoardThemes);

        Call<BoardModel> beerModelCall = apiService.getBoards();
        beerModelCall.enqueue(new Callback<BoardModel>() {
            @Override
            public void onResponse(@NonNull Call<BoardModel> call, @NonNull Response<BoardModel> response) {

                Log.d(TAG, "onResponse: " + response.toString());
                BoardModel boardModel = response.body();
                if (boardModel != null) {
                    if (boardsPublishSubject.hasObservers())
                        if (response.code() == 200) {
                            Log.d(TAG, "onResponse: +");
                            boardsPublishSubject.onNext(boardModel);
                        } else {
                            boardsPublishSubject.onNext(new BoardModel());
                        }
                }
                boardsPublishSubject.onComplete();
            }

            @Override
            public void onFailure(@NonNull Call<BoardModel> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: ");
                if (boardsPublishSubject.hasObservers())
                    boardsPublishSubject.onNext(new BoardModel());
            }

        });
        return retrofitSearchDownload;
    }

    public RetrofitSearchDvach getThreadsForBoard(String nameBoard, final io.reactivex.Observer<List<Thread>> observerThreads) {
        final PublishSubject<List<Thread>> threadsPublishSubject = SubjectFactory.createPublishSubject(observerThreads);

        Call<ThreadsModel> beerModelCall = apiService.getThreads(nameBoard);
        beerModelCall.enqueue(new Callback<ThreadsModel>() {
            @Override
            public void onResponse(@NonNull Call<ThreadsModel> call, @NonNull Response<ThreadsModel> response) {
                Log.d(TAG, "onResponse: "+response.toString());
                ThreadsModel threadsModel = response.body();
                if (threadsPublishSubject.hasObservers())
                    if (threadsModel != null) {
                        if (response.code() == 200) {
                            Log.d(TAG, "onResponse: +");
                            threadsPublishSubject.onNext(threadsModel.getThreads());
                        } else {
                            threadsPublishSubject.onNext(new ArrayList<Thread>());
                        }
                    }
                threadsPublishSubject.onComplete();
            }

            @Override
            public void onFailure(@NonNull Call<ThreadsModel> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: ");
                if (threadsPublishSubject.hasObservers())
                    threadsPublishSubject.onNext(new ArrayList<Thread>());
            }

        });
        return retrofitSearchDownload;
    }

    public RetrofitSearchDvach getPostsForThread(String nameBoard, final io.reactivex.Observer<List<Thread>> observerThreads) {
        final PublishSubject<List<Thread>> threadsPublishSubject = SubjectFactory.createPublishSubject(observerThreads);

        Call<ThreadsModel> beerModelCall = apiService.getThreads(nameBoard);
        beerModelCall.enqueue(new Callback<ThreadsModel>() {
            @Override
            public void onResponse(@NonNull Call<ThreadsModel> call, @NonNull Response<ThreadsModel> response) {
                Log.d(TAG, "onResponse: "+response.toString());
                ThreadsModel threadsModel = response.body();
                if (threadsPublishSubject.hasObservers())
                    if (threadsModel != null) {
                        if (response.code() == 200) {
                            Log.d(TAG, "onResponse: +");
                            threadsPublishSubject.onNext(threadsModel.getThreads());
                        } else {
                            threadsPublishSubject.onNext(new ArrayList<Thread>());
                        }
                    }
                threadsPublishSubject.onComplete();
            }

            @Override
            public void onFailure(@NonNull Call<ThreadsModel> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: ");
                if (threadsPublishSubject.hasObservers())
                    threadsPublishSubject.onNext(new ArrayList<Thread>());
            }

        });
        return retrofitSearchDownload;
    }
}
