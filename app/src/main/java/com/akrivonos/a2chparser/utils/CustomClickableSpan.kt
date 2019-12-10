package com.akrivonos.a2chparser.utils

import android.text.style.ClickableSpan
import android.view.View

class CustomClickableSpan(private val clickSpan: (text: String) -> Unit, private val text: String) : ClickableSpan() {

    override fun onClick(widget: View) {
       clickSpan(text)
    }
}