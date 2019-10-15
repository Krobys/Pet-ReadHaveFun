package com.akrivonos.a2chparser.pojomodel.boardmodel;

import com.google.gson.annotations.Expose;

import java.util.List;

public class BoardTheme {

    @Expose
    private String boardThemeName = null;
    @Expose
    private List<BoardConcrete> boardConcretes = null;

    public String getBoardThemeName() {
        return boardThemeName;
    }

    public void setBoardThemeName(String boardThemeName) {
        this.boardThemeName = boardThemeName;
    }

    public List<BoardConcrete> getBoardConcretes() {
        return boardConcretes;
    }

    public void setBoardConcretes(List<BoardConcrete> boardConcretes) {
        this.boardConcretes = boardConcretes;
    }


}
