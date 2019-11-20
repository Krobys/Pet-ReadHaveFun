package com.akrivonos.a2chparser.builders

import android.annotation.SuppressLint
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

object SubjectBuilder {

    fun <T> createPublishSubject(observer: Observer<T>): PublishSubject<T> {
        val subject = PublishSubject.create<T>()
        subject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        return subject
    }

    @SuppressLint("CheckResult")
    fun <T> createPublishSubject(observer: Consumer<T>): PublishSubject<T> {
        val subject = PublishSubject.create<T>()
        subject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        return subject
    }

    fun <T> createReplaySubject(observer: Observer<in T>): ReplaySubject<T> {
        val subject = ReplaySubject.create<T>()
        subject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        return subject
    }

    fun <T> createBehaviorSubject(observer: Observer<in T>): BehaviorSubject<T> {
        val subject = BehaviorSubject.create<T>()
        subject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        return subject
    }

    fun <T> createAsyncSubject(observer: Observer<in T>): AsyncSubject<T> {
        val subject = AsyncSubject.create<T>()
        subject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
        return subject
    }
}
