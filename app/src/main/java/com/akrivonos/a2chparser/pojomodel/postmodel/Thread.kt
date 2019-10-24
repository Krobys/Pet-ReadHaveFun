package com.akrivonos.a2chparser.pojomodel.postmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Thread {

    @SerializedName("posts")
    @Expose
    var posts: List<Post> = emptyList()

}
