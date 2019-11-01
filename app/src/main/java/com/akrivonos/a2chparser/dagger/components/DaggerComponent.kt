package com.akrivonos.a2chparser.dagger.components

import com.akrivonos.a2chparser.dagger.modules.NetworkModule
import com.akrivonos.a2chparser.viewmodels.BoardsViewModel
import dagger.Component


@Component(modules = [NetworkModule::class])
interface DaggerComponent {
    fun inject(viewModel: BoardsViewModel)
    //fun getItemDecoratorUtils() : ItemDecoratorUtils
    //fun getSharedPreferenceUtils() : SharedPreferenceUtils
}