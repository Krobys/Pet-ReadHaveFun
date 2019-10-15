package com.akrivonos.a2chparser.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.adapters.BoardConcreteAdapter;

public class ConcreteBoardFragment extends Fragment{

    private RecyclerView recyclerViewBoardThreads;
    private BoardConcreteAdapter boardConcreteAdapter;

    public ConcreteBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getContext();
        if(context != null){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_concrete_board, container, false);
        setUpScreen(view);
        return view;
    }

    private void startLoadThreadsForBoard(){

    }

    private void setUpScreen(View view){

        recyclerViewBoardThreads = view.findViewById(R.id.board_threads_rec_view);
        recyclerViewBoardThreads.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerViewBoardThreads.setAdapter();
    }
}
