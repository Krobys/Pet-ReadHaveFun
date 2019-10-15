package com.akrivonos.a2chparser.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.adapters.BoardConcreteAdapter;
import com.akrivonos.a2chparser.adapters.BoardThemeAdapter;
import com.akrivonos.a2chparser.interfaces.OpenBoardListener;
import com.akrivonos.a2chparser.interfaces.OpenDetailsBoardsBottomSheetListener;
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardModel;
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme;
import com.akrivonos.a2chparser.retrofit.RetrofitSearchDvach;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BoardsFragment extends Fragment implements OpenDetailsBoardsBottomSheetListener {

    private RecyclerView recyclerViewBoards;
    private BottomSheetBehavior sheetBehavior;
    private FrameLayout bottomSheet;
    private BoardThemeAdapter boardAdapter;

    public static final String BOARD_ID = "board_id";

    private Observer<BoardModel> observer = new Observer<BoardModel>() {
        Disposable disposable;
        @Override
        public void onSubscribe(Disposable d) {
            disposable = d;
        }

        @Override
        public void onNext(BoardModel boardModel) {
            final List<BoardTheme> boardThemes = boardModel.getBoardThemes();
            if(boardThemes != null){
                if(boardAdapter == null) Log.d("test", "adapter null: ");
                boardAdapter.setBoardThemes(boardThemes);
                boardAdapter.notifyDataSetChanged();
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
        Context context = getContext();
        if(context != null){
            OpenDetailsBoardsBottomSheetListener boardsBottomSheetListener = this;
            boardAdapter = new BoardThemeAdapter(context, boardsBottomSheetListener);
            Log.d("test", "setUpAdapterAndListeners: ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_boards, container, false);
        setUpScreen(fragmentView, getContext());

        startLoadBoards();
        return fragmentView;
    }

    private void setUpScreen(View view, Context context){
        if (!(view == null || context == null)){
            recyclerViewBoards = view.findViewById(R.id.boards_rec_view);
            recyclerViewBoards.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewBoards.setAdapter(boardAdapter);

            bottomSheet = view.findViewById(R.id.bottom_sheet_detailed_boards);
            sheetBehavior = BottomSheetBehavior.from(bottomSheet);
            sheetBehavior.setHideable(true);
            sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    private void startLoadBoards(){
        RetrofitSearchDvach.getInstance().getBoardsAsync(observer);
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
