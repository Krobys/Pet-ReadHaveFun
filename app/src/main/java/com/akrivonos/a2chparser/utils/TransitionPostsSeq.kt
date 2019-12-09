package com.akrivonos.a2chparser.utils

import com.akrivonos.a2chparser.interfaces.PostsMoverToListener

class TransitionPostsSeq(private val mover: PostsMoverToListener?) {

    private var startNum: String? = null

    fun goToPost(starterNum: String?, destinationNum: String?) {
        if (startNum == null) startNum = starterNum
        mover?.moveTo(destinationNum)
    }

    fun getBackStart() {
        mover?.moveTo(startNum)
        startNum = null
    }

    fun isActive(): Boolean = startNum != null
}