package com.akrivonos.a2chparser.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.interfaces.OpenDetailsBoardsBottomSheetListener;
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme;

public class ThemeBoardViewHolder extends RecyclerView.ViewHolder {

    private final TextView boardThemeNameTextView;
    private BoardTheme boardTheme;

    public void setBoardThemeName(String boardName) {
        boardThemeNameTextView.setText(boardName);
    }

    public BoardTheme getBoardTheme() {
        return boardTheme;
    }

    public void setBoardTheme(BoardTheme boardTheme) {
        this.boardTheme = boardTheme;
    }

    public ThemeBoardViewHolder(@NonNull View itemView, final OpenDetailsBoardsBottomSheetListener bottomSheetListener) {
        super(itemView);
        boardThemeNameTextView = itemView.findViewById(R.id.board_name);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetListener.openBottomSheet(boardTheme);
            }
        });
    }
}
