package com.akrivonos.a2chparser.pojomodel.threadmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsAbu {

    @SerializedName("date")
    @Expose
    var date: String? = null
    @SerializedName("num")
    @Expose
    var num: Int? = null
    @SerializedName("subject")
    @Expose
    var subject: String? = null
    @SerializedName("views")
    @Expose
    var views: Int? = null

}

