package com.akrivonos.a2chparser.dagger.modules

import com.akrivonos.a2chparser.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindBoardsFragment(): BoardsFragment

    @ContributesAndroidInjector
    abstract fun bindConcreteBoardFragment(): ConcreteBoardFragment

    @ContributesAndroidInjector
    abstract fun bindConcreteThreadFragment(): ConcreteThreadFragment

    @ContributesAndroidInjector
    abstract fun bindFavoritePageConcreteFragment(): FavoritePageConcreteFragment

    @ContributesAndroidInjector
    abstract fun bindFavoritePageThemesList(): FavoritePageThemesList
}