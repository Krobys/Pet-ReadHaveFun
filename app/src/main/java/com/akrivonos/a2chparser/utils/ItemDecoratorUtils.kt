package com.akrivonos.a2chparser.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

object ItemDecoratorUtils {

    fun createItemDecorationOffsets(direction: DecorationDirection, countDecoration: Int): RecyclerView.ItemDecoration {
        return object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                when (direction) {
                    ItemDecoratorUtils.DecorationDirection.TOP -> outRect.top = countDecoration
                    ItemDecoratorUtils.DecorationDirection.LEFT -> outRect.left = countDecoration
                    ItemDecoratorUtils.DecorationDirection.RIGHT -> outRect.right = countDecoration
                    ItemDecoratorUtils.DecorationDirection.BOTTOM -> outRect.bottom = countDecoration
                }
            }
        }
    }

    enum class DecorationDirection {
        TOP, BOTTOM, RIGHT, LEFT
    }
}
