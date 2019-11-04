package com.akrivonos.a2chparser.utils

import android.R
import android.content.Context
import androidx.recyclerview.widget.RecyclerView


abstract class HidingScrollListener(context: Context) : RecyclerView.OnScrollListener() {

    private var mToolbarOffset: Float = 0F
    private var mControlsVisible = true
    val mToolbarHeight: Float

    init {
        mToolbarHeight = getToolbarHeight(context)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (mControlsVisible) {
                if (mToolbarOffset > HIDE_THRESHOLD) {
                    setInvisible()
                } else {
                    setVisible()
                }
            } else {
                if (mToolbarHeight - mToolbarOffset > SHOW_THRESHOLD) {
                    setVisible()
                } else {
                    setInvisible()
                }
            }
        }

    }

    private fun getToolbarHeight(context: Context): Float {
        val styledAttributes = context.theme.obtainStyledAttributes(
                intArrayOf(R.attr.actionBarSize))
        val toolbarHeight = styledAttributes.getDimension(0, 0f)
        styledAttributes.recycle()

        return toolbarHeight
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        clipToolbarOffset()
        onMoved(mToolbarOffset)

        if (mToolbarOffset < mToolbarHeight && dy > 0 || mToolbarOffset > 0 && dy < 0) {
            mToolbarOffset += dy
        }

    }

    private fun clipToolbarOffset() {
        if (mToolbarOffset > mToolbarHeight) {
            mToolbarOffset = mToolbarHeight
        } else if (mToolbarOffset < 0) {
            mToolbarOffset = 0F
        }
    }

    private fun setVisible() {
        if (mToolbarOffset > 0) {
            onShow()
            mToolbarOffset = 0F
        }
        mControlsVisible = true
    }

    private fun setInvisible() {
        if (mToolbarOffset < mToolbarHeight) {
            onHide()
            mToolbarOffset = mToolbarHeight
        }
        mControlsVisible = false
    }

    abstract fun onMoved(distance: Float)
    abstract fun onShow()
    abstract fun onHide()

    companion object {

        private val HIDE_THRESHOLD = 10f
        private val SHOW_THRESHOLD = 70f
    }
}