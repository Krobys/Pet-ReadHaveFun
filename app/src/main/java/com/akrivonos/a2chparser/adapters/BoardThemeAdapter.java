package com.akrivonos.a2chparser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.a2chparser.R;
import com.akrivonos.a2chparser.adapters.viewholders.ThemeBoardViewHolder;
import com.akrivonos.a2chparser.interfaces.OpenDetailsBoardsBottomSheetListener;
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme;

import java.util.ArrayList;
import java.util.List;

public class BoardThemeAdapter extends RecyclerView.Adapter<ThemeBoardViewHolder> {

    private final LayoutInflater layoutInflater;
    private ArrayList<BoardTheme> themesBoardList = new ArrayList<>();
    private final OpenDetailsBoardsBottomSheetListener bottomSheetListener;

    public void setBoardThemes(List<BoardTheme> boardThemes){
        themesBoardList = new ArrayList<>(boardThemes);
    }

    public boolean isSet() {
        return themesBoardList.size() != 0;
    }
    public BoardThemeAdapter(Context context, OpenDetailsBoardsBottomSheetListener openDetailsBoardsBottomSheetListener){
        layoutInflater = LayoutInflater.from(context);
        bottomSheetListener = openDetailsBoardsBottomSheetListener;
    }

    @NonNull
    @Override
    public ThemeBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapteritem_theme_board, parent, false);
        return new ThemeBoardViewHolder(view, bottomSheetListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeBoardViewHolder holder, int position) {
        BoardTheme boardTheme = themesBoardList.get(position);
        holder.setBoardThemeName(boardTheme.getBoardThemeName());
        holder.setBoardTheme(boardTheme);
    }

    @Override
    public int getItemCount() {
        return themesBoardList.size();
    }
}
