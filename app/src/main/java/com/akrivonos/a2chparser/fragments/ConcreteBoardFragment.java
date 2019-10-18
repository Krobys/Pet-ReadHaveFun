package com.akrivonos.a2chparser.fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.adapters.ThreadAdapter;
import com.akrivonos.a2chparser.dialogs.MediaZoomedDialog;
import com.akrivonos.a2chparser.interfaces.PageDisplayModeListener;
import com.akrivonos.a2chparser.interfaces.SetUpToolbarModeListener;
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener;
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardConcrete;
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread;
import com.akrivonos.a2chparser.retrofit.RetrofitSearchDvach;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.akrivonos.a2chparser.MainActivity.PAGE_MODE_ONLY_TOOLBAR;
import static com.akrivonos.a2chparser.MainActivity.TOOLBAR_MODE_FULL;
import static com.akrivonos.a2chparser.fragments.BoardsFragment.BOARD_INFO;


public class ConcreteBoardFragment extends Fragment implements ShowContentMediaListener {

    private ThreadAdapter threadAdapter;
    private PageDisplayModeListener pageDisplayModeListener;
    private SetUpToolbarModeListener toolbarModeListener;

    private Observer<List<Thread>> observer = new Observer<List<Thread>>() {
        Disposable disposable;
        @Override
        public void onSubscribe(Disposable d) {
            disposable = d;
        }

        @Override
        public void onNext(List<Thread> threads) {
            if(threads != null && threads.size() > 0){
                threadAdapter.setThreads(threads);
                threadAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            disposable.dispose();
        }
    };

    public ConcreteBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpAdapterAndListeners();
    }

    private void setUpAdapterAndListeners(){
        Activity activity = getActivity();
        if(activity != null){
            pageDisplayModeListener = (PageDisplayModeListener) activity;
            toolbarModeListener = (SetUpToolbarModeListener) activity;
            ShowContentMediaListener showContentMediaListener = this;
            threadAdapter = new ThreadAdapter(activity, false, showContentMediaListener);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_concrete_board, container, false);
        setUpScreen(view, getContext());
        startLoadThreadsForBoard();
        return view;
    }

    private void startLoadThreadsForBoard(){
        Bundle arguments = getArguments();
        if(arguments != null){
            BoardConcrete boardConcrete = arguments.getParcelable(BOARD_INFO);
            if(boardConcrete != null){
                RetrofitSearchDvach.getInstance().getThreadsForBoard(boardConcrete.getId(), observer);
                toolbarModeListener.setMode(TOOLBAR_MODE_FULL, boardConcrete.getName());
            }
        }
    }

    private void setUpScreen(View view, Context context){
        if(view != null && context != null){
            RecyclerView recyclerViewBoardThreads = view.findViewById(R.id.board_threads_rec_view);
            recyclerViewBoardThreads.setLayoutManager(new LinearLayoutManager(context));
            recyclerViewBoardThreads.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    outRect.bottom = 100;
                }
            });
            recyclerViewBoardThreads.setAdapter(threadAdapter);
        }
        pageDisplayModeListener.setPageMode(PAGE_MODE_ONLY_TOOLBAR);
    }

    @Override
    public void showContent(String pathMedia, int mediaType) {
        Context context = getContext();
        if (context != null) {
            MediaZoomedDialog cdd = new MediaZoomedDialog(context, pathMedia, mediaType);
            Objects.requireNonNull(cdd.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            cdd.setCanceledOnTouchOutside(true);
            cdd.show();
        }
    }
}
