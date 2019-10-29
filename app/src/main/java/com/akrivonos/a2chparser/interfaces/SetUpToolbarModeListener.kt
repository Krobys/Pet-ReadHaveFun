package com.akrivonos.a2chparser.interfaces

import com.akrivonos.a2chparser.MainActivity

interface SetUpToolbarModeListener {
    fun setMode(mode: MainActivity.Companion.ToolbarMode, title: String?)
}
