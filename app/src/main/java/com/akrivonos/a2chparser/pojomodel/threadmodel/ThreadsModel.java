package com.akrivonos.a2chparser.pojomodel.threadmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ThreadsModel {

    @SerializedName("Board")
    @Expose
    private String board;
    @SerializedName("BoardInfo")
    @Expose
    private String boardInfo;
    @SerializedName("BoardInfoOuter")
    @Expose
    private String boardInfoOuter;
    @SerializedName("BoardName")
    @Expose
    private String boardName;
    @SerializedName("advert_bottom_image")
    @Expose
    private String advertBottomImage;
    @SerializedName("advert_bottom_link")
    @Expose
    private String advertBottomLink;
    @SerializedName("advert_mobile_image")
    @Expose
    private String advertMobileImage;
    @SerializedName("advert_mobile_link")
    @Expose
    private String advertMobileLink;
    @SerializedName("advert_top_image")
    @Expose
    private String advertTopImage;
    @SerializedName("advert_top_link")
    @Expose
    private String advertTopLink;
    @SerializedName("board_banner_image")
    @Expose
    private String boardBannerImage;
    @SerializedName("board_banner_link")
    @Expose
    private String boardBannerLink;
    @SerializedName("bump_limit")
    @Expose
    private Integer bumpLimit;
    @SerializedName("default_name")
    @Expose
    private String defaultName;
    @SerializedName("enable_dices")
    @Expose
    private Integer enableDices;
    @SerializedName("enable_flags")
    @Expose
    private Integer enableFlags;
    @SerializedName("enable_icons")
    @Expose
    private Integer enableIcons;
    @SerializedName("enable_images")
    @Expose
    private Integer enableImages;
    @SerializedName("enable_likes")
    @Expose
    private Integer enableLikes;
    @SerializedName("enable_names")
    @Expose
    private Integer enableNames;
    @SerializedName("enable_oekaki")
    @Expose
    private Integer enableOekaki;
    @SerializedName("enable_posting")
    @Expose
    private Integer enablePosting;
    @SerializedName("enable_sage")
    @Expose
    private Integer enableSage;
    @SerializedName("enable_shield")
    @Expose
    private Integer enableShield;
    @SerializedName("enable_subject")
    @Expose
    private Integer enableSubject;
    @SerializedName("enable_thread_tags")
    @Expose
    private Integer enableThreadTags;
    @SerializedName("enable_trips")
    @Expose
    private Integer enableTrips;
    @SerializedName("enable_video")
    @Expose
    private Integer enableVideo;
    @SerializedName("filter")
    @Expose
    private String filter;
    @SerializedName("max_comment")
    @Expose
    private Integer maxComment;
    @SerializedName("max_files_size")
    @Expose
    private Integer maxFilesSize;
    @SerializedName("news_abu")
    @Expose
    private List<NewsAbu> newsAbu = null;
    @SerializedName("threads")
    @Expose
    private List<Thread> threads = null;
    @SerializedName("top")
    @Expose
    private List<Top> top = null;

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getBoardInfo() {
        return boardInfo;
    }

    public void setBoardInfo(String boardInfo) {
        this.boardInfo = boardInfo;
    }

    public String getBoardInfoOuter() {
        return boardInfoOuter;
    }

    public void setBoardInfoOuter(String boardInfoOuter) {
        this.boardInfoOuter = boardInfoOuter;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getAdvertBottomImage() {
        return advertBottomImage;
    }

    public void setAdvertBottomImage(String advertBottomImage) {
        this.advertBottomImage = advertBottomImage;
    }

    public String getAdvertBottomLink() {
        return advertBottomLink;
    }

    public void setAdvertBottomLink(String advertBottomLink) {
        this.advertBottomLink = advertBottomLink;
    }

    public String getAdvertMobileImage() {
        return advertMobileImage;
    }

    public void setAdvertMobileImage(String advertMobileImage) {
        this.advertMobileImage = advertMobileImage;
    }

    public String getAdvertMobileLink() {
        return advertMobileLink;
    }

    public void setAdvertMobileLink(String advertMobileLink) {
        this.advertMobileLink = advertMobileLink;
    }

    public String getAdvertTopImage() {
        return advertTopImage;
    }

    public void setAdvertTopImage(String advertTopImage) {
        this.advertTopImage = advertTopImage;
    }

    public String getAdvertTopLink() {
        return advertTopLink;
    }

    public void setAdvertTopLink(String advertTopLink) {
        this.advertTopLink = advertTopLink;
    }

    public String getBoardBannerImage() {
        return boardBannerImage;
    }

    public void setBoardBannerImage(String boardBannerImage) {
        this.boardBannerImage = boardBannerImage;
    }

    public String getBoardBannerLink() {
        return boardBannerLink;
    }

    public void setBoardBannerLink(String boardBannerLink) {
        this.boardBannerLink = boardBannerLink;
    }

    public Integer getBumpLimit() {
        return bumpLimit;
    }

    public void setBumpLimit(Integer bumpLimit) {
        this.bumpLimit = bumpLimit;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public Integer getEnableDices() {
        return enableDices;
    }

    public void setEnableDices(Integer enableDices) {
        this.enableDices = enableDices;
    }

    public Integer getEnableFlags() {
        return enableFlags;
    }

    public void setEnableFlags(Integer enableFlags) {
        this.enableFlags = enableFlags;
    }

    public Integer getEnableIcons() {
        return enableIcons;
    }

    public void setEnableIcons(Integer enableIcons) {
        this.enableIcons = enableIcons;
    }

    public Integer getEnableImages() {
        return enableImages;
    }

    public void setEnableImages(Integer enableImages) {
        this.enableImages = enableImages;
    }

    public Integer getEnableLikes() {
        return enableLikes;
    }

    public void setEnableLikes(Integer enableLikes) {
        this.enableLikes = enableLikes;
    }

    public Integer getEnableNames() {
        return enableNames;
    }

    public void setEnableNames(Integer enableNames) {
        this.enableNames = enableNames;
    }

    public Integer getEnableOekaki() {
        return enableOekaki;
    }

    public void setEnableOekaki(Integer enableOekaki) {
        this.enableOekaki = enableOekaki;
    }

    public Integer getEnablePosting() {
        return enablePosting;
    }

    public void setEnablePosting(Integer enablePosting) {
        this.enablePosting = enablePosting;
    }

    public Integer getEnableSage() {
        return enableSage;
    }

    public void setEnableSage(Integer enableSage) {
        this.enableSage = enableSage;
    }

    public Integer getEnableShield() {
        return enableShield;
    }

    public void setEnableShield(Integer enableShield) {
        this.enableShield = enableShield;
    }

    public Integer getEnableSubject() {
        return enableSubject;
    }

    public void setEnableSubject(Integer enableSubject) {
        this.enableSubject = enableSubject;
    }

    public Integer getEnableThreadTags() {
        return enableThreadTags;
    }

    public void setEnableThreadTags(Integer enableThreadTags) {
        this.enableThreadTags = enableThreadTags;
    }

    public Integer getEnableTrips() {
        return enableTrips;
    }

    public void setEnableTrips(Integer enableTrips) {
        this.enableTrips = enableTrips;
    }

    public Integer getEnableVideo() {
        return enableVideo;
    }

    public void setEnableVideo(Integer enableVideo) {
        this.enableVideo = enableVideo;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Integer getMaxComment() {
        return maxComment;
    }

    public void setMaxComment(Integer maxComment) {
        this.maxComment = maxComment;
    }

    public Integer getMaxFilesSize() {
        return maxFilesSize;
    }

    public void setMaxFilesSize(Integer maxFilesSize) {
        this.maxFilesSize = maxFilesSize;
    }

    public List<NewsAbu> getNewsAbu() {
        return newsAbu;
    }

    public void setNewsAbu(List<NewsAbu> newsAbu) {
        this.newsAbu = newsAbu;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    public List<Top> getTop() {
        return top;
    }

    public void setTop(List<Top> top) {
        this.top = top;
    }

}
