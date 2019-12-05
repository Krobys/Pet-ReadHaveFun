package com.akrivonos.a2chparser.interfaces

interface CallBack {
    fun call()
}

interface CallBackP<T> {
    fun call(t: T)
}