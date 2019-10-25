package com.akrivonos.a2chparser.pojomodel.threadmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Top {

    @SerializedName("board")
    @Expose
    var board: String? = null
    @SerializedName("info")
    @Expose
    var info: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

}
