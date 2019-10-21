package com.akrivonos.a2chparser.adapters.viewholders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.database.RoomAppDatabase;
import com.akrivonos.a2chparser.interfaces.OpenBoardListener;
import com.akrivonos.a2chparser.models.database.Board;
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardConcrete;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BoardConcreteViewHolder extends RecyclerView.ViewHolder {

    public final TextView nameBoardTextView;
    public final TextView idBoardTextView;
    public final ImageButton imageButtonSave;
    private Board board;
    private RoomAppDatabase database;

    public BoardConcreteViewHolder(@NonNull View itemView, final OpenBoardListener openBoardListener, RoomAppDatabase database) {
        super(itemView);
        nameBoardTextView = itemView.findViewById(R.id.name_board);
        idBoardTextView = itemView.findViewById(R.id.id_board);
        imageButtonSave = itemView.findViewById(R.id.button_is_save);
        this.database = database;

        itemView.setOnClickListener(view -> {
            if (board != null) {
                openBoardListener.openBoard(board);
            }
        });
    }

    public Disposable setBoardConcrete(BoardConcrete boardConcrete) {
        this.board = new Board(boardConcrete);
        nameBoardTextView.setText(boardConcrete.getName());
        idBoardTextView.setText("/".concat(boardConcrete.getId()).concat("/"));
        return database.boardsDao().getBoardById(boardConcrete.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> imageButtonSave.setBackgroundResource((list.size() != 0)
                        ? R.drawable.ic_star_save
                        : R.drawable.ic_star_not_save));
    }
}
