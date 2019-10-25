package com.akrivonos.a2chparser.pojomodel.threadmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class File {

    @SerializedName("displayname")
    @Expose
    var displayname: String? = null
    @SerializedName("fullname")
    @Expose
    var fullname: String? = null
    @SerializedName("height")
    @Expose
    var height: String? = null
    @SerializedName("md5")
    @Expose
    var md5: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("nsfw")
    @Expose
    var nsfw: String? = null
    @SerializedName("path")
    @Expose
    var path: String? = null
    @SerializedName("size")
    @Expose
    var size: String? = null
    @SerializedName("thumbnail")
    @Expose
    var thumbnail: String? = null
    @SerializedName("tn_height")
    @Expose
    var tnHeight: String? = null
    @SerializedName("tn_width")
    @Expose
    var tnWidth: String? = null
    @SerializedName("type")
    @Expose
    var type: Int? = null
    @SerializedName("width")
    @Expose
    var width: String? = null
    @SerializedName("duration")
    @Expose
    var duration: String? = null
    @SerializedName("duration_secs")
    @Expose
    var durationSecs: String? = null

}
