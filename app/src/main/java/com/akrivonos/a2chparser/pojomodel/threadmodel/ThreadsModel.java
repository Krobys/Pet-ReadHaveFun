package com.akrivonos.a2chparser.pojomodel.threadmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ThreadsModel {

    @SerializedName("board")
    @Expose
    private String board;
    @SerializedName("threads")
    @Expose
    private List<Thread> threads = null;

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

}
