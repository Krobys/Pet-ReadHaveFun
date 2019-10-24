package com.akrivonos.a2chparser.pojomodel.boardmodel;

import android.content.Context;

import com.akrivonos.a2chparser.utils.SharedPreferenceUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BoardModel {

    @SerializedName("Взрослым")
    @Expose
    private
    ArrayList<BoardConcrete> adult = null;
    @SerializedName("Игры")
    @Expose
    private
    ArrayList < BoardConcrete > games = null;
    @SerializedName("Политика")
    @Expose
    private
    ArrayList < BoardConcrete > politic = null;
    @SerializedName("Пользовательские")
    @Expose
    private
    ArrayList < BoardConcrete > custom = null;
    @SerializedName("Разное")
    @Expose
    private
    ArrayList < BoardConcrete > different = null;
    @SerializedName("Творчество")
    @Expose
    private
    ArrayList < BoardConcrete > art = null;
    @SerializedName("Тематика")
    @Expose
    private
    ArrayList < BoardConcrete > theme = null;
    @SerializedName("Техника и софт")
    @Expose
    private
    ArrayList < BoardConcrete > tech = null;
    @SerializedName("Японская культура")
    @Expose
    private
    ArrayList < BoardConcrete > japan = null;

    public List<BoardTheme> getBoardThemes(Context context) {
        List<BoardTheme> boardThemes = new ArrayList<>();
        boolean isAdult = SharedPreferenceUtils.INSTANCE.getAdultSetting(context);
        if (isAdult)
            boardThemes.add(new BoardTheme(adult));
        boardThemes.add(new BoardTheme(games));
        boardThemes.add(new BoardTheme(politic));
        boardThemes.add(new BoardTheme(custom));
        boardThemes.add(new BoardTheme(different));
        boardThemes.add(new BoardTheme(art));
        boardThemes.add(new BoardTheme(theme));
        boardThemes.add(new BoardTheme(tech));
        boardThemes.add(new BoardTheme(japan));
        return boardThemes;
    }

    public ArrayList<BoardConcrete> getAdult() {
        return adult;
    }

    public void setAdult(ArrayList<BoardConcrete> adult) {
        this.adult = adult;
    }

    public ArrayList<BoardConcrete> getGames() {
        return games;
    }

    public void setGames(ArrayList<BoardConcrete> games) {
        this.games = games;
    }

    public ArrayList<BoardConcrete> getPolitic() {
        return politic;
    }

    public void setPolitic(ArrayList<BoardConcrete> politic) {
        this.politic = politic;
    }

    public ArrayList<BoardConcrete> getCustom() {
        return custom;
    }

    public void setCustom(ArrayList<BoardConcrete> custom) {
        this.custom = custom;
    }

    public ArrayList<BoardConcrete> getDifferent() {
        return different;
    }

    public void setDifferent(ArrayList<BoardConcrete> different) {
        this.different = different;
    }

    public ArrayList<BoardConcrete> getArt() {
        return art;
    }

    public void setArt(ArrayList<BoardConcrete> art) {
        this.art = art;
    }

    public ArrayList<BoardConcrete> getTheme() {
        return theme;
    }

    public void setTheme(ArrayList<BoardConcrete> theme) {
        this.theme = theme;
    }

    public ArrayList<BoardConcrete> getTech() {
        return tech;
    }

    public void setTech(ArrayList<BoardConcrete> tech) {
        this.tech = tech;
    }

    public ArrayList<BoardConcrete> getJapan() {
        return japan;
    }

    public void setJapan(ArrayList<BoardConcrete> japan) {
        this.japan = japan;
    }

}
