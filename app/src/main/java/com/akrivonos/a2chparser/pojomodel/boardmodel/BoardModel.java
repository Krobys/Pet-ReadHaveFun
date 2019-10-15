package com.akrivonos.a2chparser.pojomodel.boardmodel;

import com.google.gson.annotations.Expose;

import java.util.List;

public class BoardModel {
    public List<BoardTheme> getBoardsThemes() {
        return boardsThemes;
    }

    public void setBoardsThemes(List<BoardTheme> boardsThemes) {
        this.boardsThemes = boardsThemes;
    }

    @Expose
    private List<BoardTheme> boardsThemes = null;
}
