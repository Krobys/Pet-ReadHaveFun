package com.akrivonos.a2chparser.dagger.modules.builders

import android.app.Application
import com.akrivonos.a2chparser.DvachApplication
import com.akrivonos.a2chparser.activities.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @Binds
    abstract fun bindDvachApplication(app: DvachApplication): Application

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

}