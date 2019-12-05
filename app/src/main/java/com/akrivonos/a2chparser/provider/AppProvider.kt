package com.akrivonos.a2chparser.provider

import android.app.Application
import io.reactivex.subjects.PublishSubject

object AppProvider {
    private val subject = PublishSubject.create<Unit>()

    lateinit var appInstance: Application

    fun provideRetrySubject(): PublishSubject<Unit> = subject

}