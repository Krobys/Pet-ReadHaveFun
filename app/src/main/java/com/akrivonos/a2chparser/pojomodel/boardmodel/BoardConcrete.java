package com.akrivonos.a2chparser.pojomodel.boardmodel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BoardConcrete implements Parcelable {
    private float bump_limit;
    private String category;
    private String default_name;
    private float enable_dices;
    private float enable_flags;
    private float enable_icons;
    private float enable_likes;
    private float enable_names;
    private float enable_oekaki;
    private float enable_posting;
    private float enable_sage;
    private float enable_shield;
    private float enable_subject;
    private float enable_thread_tags;
    private float enable_trips;
    private List<Icons> icons = null;
    private String id;
    private String name;
    private float pages;
    private float sage;
    private float tripcodes;

    // Getter Methods

    protected BoardConcrete(Parcel in) {
        bump_limit = in.readFloat();
        category = in.readString();
        default_name = in.readString();
        enable_dices = in.readFloat();
        enable_flags = in.readFloat();
        enable_icons = in.readFloat();
        enable_likes = in.readFloat();
        enable_names = in.readFloat();
        enable_oekaki = in.readFloat();
        enable_posting = in.readFloat();
        enable_sage = in.readFloat();
        enable_shield = in.readFloat();
        enable_subject = in.readFloat();
        enable_thread_tags = in.readFloat();
        enable_trips = in.readFloat();
        id = in.readString();
        name = in.readString();
        pages = in.readFloat();
        sage = in.readFloat();
        tripcodes = in.readFloat();
    }

    public static final Creator<BoardConcrete> CREATOR = new Creator<BoardConcrete>() {
        @Override
        public BoardConcrete createFromParcel(Parcel in) {
            return new BoardConcrete(in);
        }

        @Override
        public BoardConcrete[] newArray(int size) {
            return new BoardConcrete[size];
        }
    };

    public float getBump_limit() {
        return bump_limit;
    }

    public String getCategory() {
        return category;
    }

    public String getDefault_name() {
        return default_name;
    }

    public float getEnable_dices() {
        return enable_dices;
    }

    public float getEnable_flags() {
        return enable_flags;
    }

    public float getEnable_icons() {
        return enable_icons;
    }

    public float getEnable_likes() {
        return enable_likes;
    }

    public float getEnable_names() {
        return enable_names;
    }

    public float getEnable_oekaki() {
        return enable_oekaki;
    }

    public float getEnable_posting() {
        return enable_posting;
    }

    public float getEnable_sage() {
        return enable_sage;
    }

    public float getEnable_shield() {
        return enable_shield;
    }

    public float getEnable_subject() {
        return enable_subject;
    }

    public float getEnable_thread_tags() {
        return enable_thread_tags;
    }

    public float getEnable_trips() {
        return enable_trips;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPages() {
        return pages;
    }

    public float getSage() {
        return sage;
    }

    public float getTripcodes() {
        return tripcodes;
    }

    // Setter Methods

    public void setBump_limit(float bump_limit) {
        this.bump_limit = bump_limit;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDefault_name(String default_name) {
        this.default_name = default_name;
    }

    public void setEnable_dices(float enable_dices) {
        this.enable_dices = enable_dices;
    }

    public void setEnable_flags(float enable_flags) {
        this.enable_flags = enable_flags;
    }

    public void setEnable_icons(float enable_icons) {
        this.enable_icons = enable_icons;
    }

    public void setEnable_likes(float enable_likes) {
        this.enable_likes = enable_likes;
    }

    public void setEnable_names(float enable_names) {
        this.enable_names = enable_names;
    }

    public void setEnable_oekaki(float enable_oekaki) {
        this.enable_oekaki = enable_oekaki;
    }

    public void setEnable_posting(float enable_posting) {
        this.enable_posting = enable_posting;
    }

    public void setEnable_sage(float enable_sage) {
        this.enable_sage = enable_sage;
    }

    public void setEnable_shield(float enable_shield) {
        this.enable_shield = enable_shield;
    }

    public void setEnable_subject(float enable_subject) {
        this.enable_subject = enable_subject;
    }

    public void setEnable_thread_tags(float enable_thread_tags) {
        this.enable_thread_tags = enable_thread_tags;
    }

    public void setEnable_trips(float enable_trips) {
        this.enable_trips = enable_trips;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPages(float pages) {
        this.pages = pages;
    }

    public void setSage(float sage) {
        this.sage = sage;
    }

    public void setTripcodes(float tripcodes) {
        this.tripcodes = tripcodes;
    }

    public List<Icons> getIcons() {
        return icons;
    }

    public void setIcons(List<Icons> icons) {
        this.icons = icons;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(bump_limit);
        parcel.writeString(category);
        parcel.writeString(default_name);
        parcel.writeFloat(enable_dices);
        parcel.writeFloat(enable_flags);
        parcel.writeFloat(enable_icons);
        parcel.writeFloat(enable_likes);
        parcel.writeFloat(enable_names);
        parcel.writeFloat(enable_oekaki);
        parcel.writeFloat(enable_posting);
        parcel.writeFloat(enable_sage);
        parcel.writeFloat(enable_shield);
        parcel.writeFloat(enable_subject);
        parcel.writeFloat(enable_thread_tags);
        parcel.writeFloat(enable_trips);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeFloat(pages);
        parcel.writeFloat(sage);
        parcel.writeFloat(tripcodes);
    }
}
