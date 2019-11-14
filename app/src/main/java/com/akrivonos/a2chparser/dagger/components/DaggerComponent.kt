package com.akrivonos.a2chparser.dagger.components

import com.akrivonos.a2chparser.dialogs.AdulthoodDialog
import com.akrivonos.a2chparser.fragments.*
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardModel
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
import com.akrivonos.a2chparser.viewmodels.BoardsViewModel
import com.akrivonos.a2chparser.viewmodels.ConcreteBoardViewModel
import com.akrivonos.a2chparser.viewmodels.ConcreteThreadViewModel
import dagger.Component


@Component
interface DaggerComponent {
    fun inject(viewModel: BoardsViewModel)
    fun inject(viewModel: ConcreteBoardViewModel)
    fun inject(viewModel: ConcreteThreadViewModel)
    fun inject(boardsFragment: BoardsFragment)
    fun inject(concreteBoardFragment: ConcreteBoardFragment)
    fun inject(concreteThreadFragment: ConcreteThreadFragment)
    fun inject(favoritePageConcreteFragment: FavoritePageConcreteFragment)
    fun inject(favoritePageThemesList: FavoritePageThemesList)
    fun inject(adulthoodDialog: AdulthoodDialog)
    fun inject(boardModel: BoardModel)
    fun getPreferenceUtils(): SharedPreferenceUtils
    fun getItemDecorator(): ItemDecoratorUtils
}