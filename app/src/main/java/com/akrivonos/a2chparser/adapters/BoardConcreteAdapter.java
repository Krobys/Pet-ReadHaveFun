package com.akrivonos.a2chparser.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.adapters.viewholders.BoardConcreteViewHolder;
import com.akrivonos.a2chparser.database.RoomAppDatabase;
import com.akrivonos.a2chparser.interfaces.OpenBoardListener;
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardConcrete;

import java.util.ArrayList;
import java.util.List;

public class BoardConcreteAdapter extends RecyclerView.Adapter<BoardConcreteViewHolder> {

    private ArrayList<BoardConcrete> boardConcretes = new ArrayList<>();
    private final LayoutInflater layoutInflater;
    private final OpenBoardListener openBoardListener;
    private final RoomAppDatabase appDatabase;

    public BoardConcreteAdapter(Context context, OpenBoardListener openBoardListener){
        layoutInflater = LayoutInflater.from(context);
        this.openBoardListener = openBoardListener;
        appDatabase = RoomAppDatabase.Companion.getAppDataBase(context);
    }

    public void setBoardConcretes(List<BoardConcrete> boardConcretes){
        this.boardConcretes = new ArrayList<>(boardConcretes);
    }

    @NonNull
    @Override
    public BoardConcreteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapteritem_concrete_board, parent, false);
        return new BoardConcreteViewHolder(view, openBoardListener, appDatabase);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardConcreteViewHolder holder, int position) {
        BoardConcrete boardConcrete = boardConcretes.get(position);
        if(boardConcrete != null){
            holder.setBoardConcrete(boardConcrete);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        Log.d("test", "onDetachedFromRecyclerView: ");
    }

    @Override
    public int getItemCount() {
        return boardConcretes.size();
    }
}
