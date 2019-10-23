package com.akrivonos.a2chparser.pojomodel.threadmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class File {

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @SerializedName("displayname")
    @Expose
    private String displayname;
    @SerializedName("fullname")
    @Expose
    private String fullname;

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("md5")
    @Expose
    private String md5;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nsfw")
    @Expose
    private String nsfw;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("tn_height")
    @Expose
    private String tnHeight;
    @SerializedName("tn_width")
    @Expose
    private String tnWidth;
    @SerializedName("type")
    @Expose
    private Integer type;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("duration_secs")
    @Expose
    private String durationSecs;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getNsfw() {
        return nsfw;
    }

    public void setNsfw(String nsfw) {
        this.nsfw = nsfw;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTnHeight() {
        return tnHeight;
    }

    public void setTnHeight(String tnHeight) {
        this.tnHeight = tnHeight;
    }

    public String getTnWidth() {
        return tnWidth;
    }

    public void setTnWidth(String tnWidth) {
        this.tnWidth = tnWidth;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getDurationSecs() {
        return durationSecs;
    }

    public void setDurationSecs(String durationSecs) {
        this.durationSecs = durationSecs;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
