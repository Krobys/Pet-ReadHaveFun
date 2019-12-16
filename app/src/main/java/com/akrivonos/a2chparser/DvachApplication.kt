package com.akrivonos.a2chparser

import com.akrivonos.a2chparser.dagger.components.DaggerAppComponent
import com.akrivonos.a2chparser.provider.AppProvider
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class DvachApplication : DaggerApplication(), HasAndroidInjector {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppProvider.appInstance = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent.builder().create(this)

}