package com.akrivonos.a2chparser.pojomodel.postmodel

import com.akrivonos.a2chparser.pojomodel.threadmodel.NewsAbu
import com.akrivonos.a2chparser.pojomodel.threadmodel.Top
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostModel {

    @SerializedName("Board")
    @Expose
    var board: String? = null
    @SerializedName("BoardInfo")
    @Expose
    var boardInfo: String? = null
    @SerializedName("BoardInfoOuter")
    @Expose
    var boardInfoOuter: String? = null
    @SerializedName("BoardName")
    @Expose
    var boardName: String? = null
    @SerializedName("advert_bottom_image")
    @Expose
    var advertBottomImage: String? = null
    @SerializedName("advert_bottom_link")
    @Expose
    var advertBottomLink: String? = null
    @SerializedName("advert_mobile_image")
    @Expose
    var advertMobileImage: String? = null
    @SerializedName("advert_mobile_link")
    @Expose
    var advertMobileLink: String? = null
    @SerializedName("advert_top_image")
    @Expose
    var advertTopImage: String? = null
    @SerializedName("advert_top_link")
    @Expose
    var advertTopLink: String? = null
    @SerializedName("board_banner_image")
    @Expose
    var boardBannerImage: String? = null
    @SerializedName("board_banner_link")
    @Expose
    var boardBannerLink: String? = null
    @SerializedName("bump_limit")
    @Expose
    var bumpLimit: Int? = null
    @SerializedName("current_thread")
    @Expose
    var currentThread: String? = null
    @SerializedName("default_name")
    @Expose
    var defaultName: String? = null
    @SerializedName("enable_dices")
    @Expose
    var enableDices: Int? = null
    @SerializedName("enable_flags")
    @Expose
    var enableFlags: Int? = null
    @SerializedName("enable_icons")
    @Expose
    var enableIcons: Int? = null
    @SerializedName("enable_images")
    @Expose
    var enableImages: Int? = null
    @SerializedName("enable_likes")
    @Expose
    var enableLikes: Int? = null
    @SerializedName("enable_names")
    @Expose
    var enableNames: Int? = null
    @SerializedName("enable_oekaki")
    @Expose
    var enableOekaki: Int? = null
    @SerializedName("enable_posting")
    @Expose
    var enablePosting: Int? = null
    @SerializedName("enable_sage")
    @Expose
    var enableSage: Int? = null
    @SerializedName("enable_shield")
    @Expose
    var enableShield: Int? = null
    @SerializedName("enable_subject")
    @Expose
    var enableSubject: Int? = null
    @SerializedName("enable_thread_tags")
    @Expose
    var enableThreadTags: Int? = null
    @SerializedName("enable_trips")
    @Expose
    var enableTrips: Int? = null
    @SerializedName("enable_video")
    @Expose
    var enableVideo: Int? = null
    @SerializedName("files_count")
    @Expose
    var filesCount: Int? = null
    @SerializedName("is_board")
    @Expose
    var isBoard: Int? = null
    @SerializedName("is_closed")
    @Expose
    var isClosed: Int? = null
    @SerializedName("is_index")
    @Expose
    var isIndex: Int? = null
    @SerializedName("max_comment")
    @Expose
    var maxComment: Int? = null
    @SerializedName("max_files_size")
    @Expose
    var maxFilesSize: Int? = null
    @SerializedName("max_num")
    @Expose
    var maxNum: Int? = null
    @SerializedName("news_abu")
    @Expose
    var newsAbu: List<NewsAbu>? = null
    @SerializedName("posts_count")
    @Expose
    var postsCount: Int? = null
    @SerializedName("threads")
    @Expose
    var threads: List<Thread>? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("top")
    @Expose
    var top: List<Top>? = null
    @SerializedName("unique_posters")
    @Expose
    var uniquePosters: String? = null

}
