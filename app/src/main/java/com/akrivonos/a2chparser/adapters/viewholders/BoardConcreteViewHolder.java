package com.akrivonos.a2chparser.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.interfaces.OpenBoardListener;

public class BoardConcreteViewHolder extends RecyclerView.ViewHolder {
    private TextView nameBoardTextView;
    private TextView idBoardTextView;
    private String idBoard;

    public void setIdBoard(String idBoard) {
        this.idBoard = idBoard;
    }

    public void setNameBoardTextView(String nameBoard) {
        nameBoardTextView.setText(nameBoard);
    }

    public void setIdBoardTextView(String idBoard) {
        idBoardTextView.setText(idBoard);
        this.idBoard = idBoard;
    }

    public String getIdBoard() {
        return idBoard;
    }

    public BoardConcreteViewHolder(@NonNull View itemView, final OpenBoardListener openBoardListener) {
        super(itemView);
        nameBoardTextView = itemView.findViewById(R.id.name_board);
        idBoardTextView = itemView.findViewById(R.id.id_board);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBoardListener.openBoard(idBoard);
            }
        });
    }
}
