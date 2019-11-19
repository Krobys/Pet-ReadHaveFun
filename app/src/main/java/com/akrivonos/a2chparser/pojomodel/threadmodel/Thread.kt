package com.akrivonos.a2chparser.pojomodel.threadmodel

import com.akrivonos.a2chparser.interfaces.FilteredItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Thread : FilteredItem{
    override fun getText(): String? = comment

    @SerializedName("banned")
    @Expose
    var banned: Int? = null
    @SerializedName("closed")
    @Expose
    var closed: Int? = null
    @SerializedName("comment")
    @Expose
    var comment: String? = null
    @SerializedName("date")
    @Expose
    var date: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("endless")
    @Expose
    var endless: Int? = null
    @SerializedName("files")
    @Expose
    var files: List<File>? = null
    @SerializedName("files_count")
    @Expose
    var filesCount: Int? = null
    @SerializedName("lasthit")
    @Expose
    var lasthit: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("num")
    @Expose
    var num: String? = null
    @SerializedName("op")
    @Expose
    var op: Int? = null
    @SerializedName("parent")
    @Expose
    var parent: String? = null
    @SerializedName("posts_count")
    @Expose
    var postsCount: Int? = null
    @SerializedName("sticky")
    @Expose
    var sticky: Int? = null
    @SerializedName("subject")
    @Expose
    var subject: String? = null
    @SerializedName("tags")
    @Expose
    var tags: String? = null
    @SerializedName("timestamp")
    @Expose
    var timestamp: Int? = null
    @SerializedName("trip")
    @Expose
    var trip: String? = null

}
