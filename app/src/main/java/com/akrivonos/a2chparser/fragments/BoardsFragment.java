package com.akrivonos.a2chparser.fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.adapters.BoardConcreteAdapter;
import com.akrivonos.a2chparser.adapters.BoardThemeAdapter;
import com.akrivonos.a2chparser.dialogs.AdulthoodDialog;
import com.akrivonos.a2chparser.interfaces.CallBack;
import com.akrivonos.a2chparser.interfaces.OpenBoardListener;
import com.akrivonos.a2chparser.interfaces.OpenDetailsBoardsBottomSheetListener;
import com.akrivonos.a2chparser.interfaces.PageDisplayModeListener;
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardModel;
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme;
import com.akrivonos.a2chparser.retrofit.RetrofitSearchDvach;
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.akrivonos.a2chparser.MainActivity.PAGE_MODE_ONLY_NAVBAR;

public class BoardsFragment extends Fragment implements OpenDetailsBoardsBottomSheetListener {

    private RecyclerView recyclerViewBoards;
    private BottomSheetBehavior sheetBehavior;
    private FrameLayout bottomSheet;
    private BoardThemeAdapter boardAdapter;
    private PageDisplayModeListener pageDisplayModeListener;
    private ProgressBar progressBarBoards;

    public static final String BOARD_INFO = "board_info";

    private Observer<BoardModel> observer = new Observer<BoardModel>() {
        Disposable disposable;
        @Override
        public void onSubscribe(Disposable d) {
            disposable = d;
        }

        @Override
        public void onNext(BoardModel boardModel) {
            final List<BoardTheme> boardThemes = boardModel.getBoardThemes(getContext());
            if(boardThemes != null){
                boardAdapter.setBoardThemes(boardThemes);
                boardAdapter.notifyDataSetChanged();
                progressBarBoards.setVisibility(View.GONE);
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            Log.d("test", "onComplete: ");
            disposable.dispose();
        }
    };

    public BoardsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpAdapterAndListeners();
    }

    private void setUpAdapterAndListeners(){
        Activity activity = getActivity();
        if(activity != null){
            OpenDetailsBoardsBottomSheetListener boardsBottomSheetListener = this;
            pageDisplayModeListener = (PageDisplayModeListener) activity;
            boardAdapter = new BoardThemeAdapter(activity, boardsBottomSheetListener);
            Log.d("test", "setUpAdapterAndListeners: ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_boards, container, false);
        setUpScreen(fragmentView, getContext());
        manageLoadBoardsInformation();
        return fragmentView;
    }

    private void manageLoadBoardsInformation() {
        if (!boardAdapter.isSet()) {
            Context context = getContext();
            if (context != null) {
                if (!SharedPreferenceUtils.isAdultSettingsSet(context)) {
                    showAdultDialog(context);
                } else {
                    startLoadBoards();
                }
            }
        }
    }

    private void showAdultDialog(Context context) {
        AdulthoodDialog cdd = new AdulthoodDialog(context, new CallBack() {
            @Override
            public void call() {
                startLoadBoards();
            }
        });
        Objects.requireNonNull(cdd.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.setCanceledOnTouchOutside(false);
        cdd.show();
    }

    private void setUpScreen(View view, Context context){
        if (!(view == null || context == null)){
            recyclerViewBoards = view.findViewById(R.id.boards_rec_view);
            recyclerViewBoards.setLayoutManager(new LinearLayoutManager(context));
            recyclerViewBoards.setAdapter(boardAdapter);

            progressBarBoards = view.findViewById(R.id.progressBarBoardsTheme);
            bottomSheet = view.findViewById(R.id.bottom_sheet_detailed_boards);
            sheetBehavior = BottomSheetBehavior.from(bottomSheet);
            sheetBehavior.setHideable(true);
            sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        pageDisplayModeListener.setPageMode(PAGE_MODE_ONLY_NAVBAR);
    }

    private void startLoadBoards(){
        RetrofitSearchDvach.getInstance().getBoardsAsync(observer);
        progressBarBoards.setVisibility(View.VISIBLE);
    }

    private void setUpBottomSheetCurrent(BoardTheme boardTheme){
        Activity activity = getActivity();
        if(activity!=null){
            OpenBoardListener openBoardListener = (OpenBoardListener) activity;
            BoardConcreteAdapter boardConcreteAdapter = new BoardConcreteAdapter(activity, openBoardListener);
            boardConcreteAdapter.setBoardConcretes(boardTheme.getBoardConcretes());
            Log.d("test", "setUpBottomSheetCurrent: "+boardTheme.getBoardConcretes().get(0).getName());
            RecyclerView bottomSheetBoardsRecView = bottomSheet.findViewById(R.id.rec_view_boards_for_theme);
            bottomSheetBoardsRecView.setLayoutManager(new LinearLayoutManager(activity));
            bottomSheetBoardsRecView.setAdapter(boardConcreteAdapter);
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            Log.d("test", "setUpBottomSheetCurrent: expand");
        }
    }

    @Override
    public void openBottomSheet(BoardTheme boardTheme) {
        setUpBottomSheetCurrent(boardTheme);
    }

}
