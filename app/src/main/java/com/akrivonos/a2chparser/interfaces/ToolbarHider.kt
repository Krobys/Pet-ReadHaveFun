package com.akrivonos.a2chparser.interfaces

interface ToolbarHider {
    fun hide(toolbarHeight: Float)
    fun show()
    fun moved(distance: Float)
}