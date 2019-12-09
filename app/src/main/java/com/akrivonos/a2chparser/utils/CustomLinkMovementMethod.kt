package com.akrivonos.a2chparser.utils

import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.view.View
import android.widget.TextView


class CustomLinkMovementMethod : LinkMovementMethod() {
    private val spansProxy: HashMap<Class<*>, SpanProxy> = HashMap()
    fun setSpanProxy(spanClazz: Class<*>, proxy: SpanProxy): CustomLinkMovementMethod {
        spansProxy[spanClazz] = proxy
        return this
    }

    fun removeSpanProxy(spanClazz: Class<*>?): CustomLinkMovementMethod {
        spansProxy.remove(spanClazz)
        return this
    }

    override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
        val action = event.action
        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            var x = event.x.toInt()
            var y = event.y.toInt()
            x -= widget.totalPaddingLeft
            y -= widget.totalPaddingTop
            x += widget.scrollX
            y += widget.scrollY
            val layout = widget.layout
            val line = layout.getLineForVertical(y)
            val off = layout.getOffsetForHorizontal(line, x.toFloat())
            val link = buffer.getSpans(off, off, ClickableSpan::class.java)
            if (link.isNotEmpty()) {
                if (action == MotionEvent.ACTION_UP) {
                    val spanProxy = spansProxy[ClickableSpan::class.java]
                    if (spanProxy != null) {
                        spanProxy.proxySpan(link[0], widget)
                    } else {
                        link[0].onClick(widget)
                    }
                    return true
                }
            }
        }
        return super.onTouchEvent(widget, buffer, event)
    }

    interface SpanProxy {
        fun proxySpan(span: Any?, widget: View?)
    }
}