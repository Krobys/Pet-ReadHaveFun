package com.akrivonos.a2chparser.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.interfaces.OpenBoardListener;
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardConcrete;

public class BoardConcreteViewHolder extends RecyclerView.ViewHolder {

    public TextView nameBoardTextView;
    public TextView idBoardTextView;

    private BoardConcrete boardConcrete;

    public BoardConcrete getBoardConcrete() {
        return boardConcrete;
    }

    public void setBoardConcrete(BoardConcrete boardConcrete) {
        this.boardConcrete = boardConcrete;
    }

    public BoardConcreteViewHolder(@NonNull View itemView, final OpenBoardListener openBoardListener) {
        super(itemView);
        nameBoardTextView = itemView.findViewById(R.id.name_board);
        idBoardTextView = itemView.findViewById(R.id.id_board);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(boardConcrete != null){
                    openBoardListener.openBoard(boardConcrete);
                }
            }
        });
    }
}
