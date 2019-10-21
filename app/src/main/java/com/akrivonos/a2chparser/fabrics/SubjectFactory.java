package com.akrivonos.a2chparser.fabrics;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class SubjectFactory {

    public static <T> PublishSubject<T> createPublishSubject(Observer<? super T> observer) {
        final PublishSubject<T> subject = PublishSubject.create();
        subject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return subject;
    }

    public static <T> ReplaySubject<T> createReplaySubject(Observer<? super T> observer) {
        final ReplaySubject<T> subject = ReplaySubject.create();
        subject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return subject;
    }

    public static <T> BehaviorSubject<T> createBehaviorSubject(Observer<? super T> observer) {
        final BehaviorSubject<T> subject = BehaviorSubject.create();
        subject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return subject;
    }

    public static <T> AsyncSubject<T> createAsyncSubject(Observer<? super T> observer) {
        final AsyncSubject<T> subject = AsyncSubject.create();
        subject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return subject;
    }
}
