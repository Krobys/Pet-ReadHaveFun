package com.akrivonos.a2chparser.dagger.components

import com.akrivonos.a2chparser.viewmodels.BoardsViewModel
import com.akrivonos.a2chparser.viewmodels.ConcreteBoardViewModel
import com.akrivonos.a2chparser.viewmodels.ConcreteThreadViewModel
import dagger.Component


@Component
interface DaggerComponent {
    fun inject(viewModel: BoardsViewModel)
    fun inject(viewModel: ConcreteBoardViewModel)
    fun inject(viewModel: ConcreteThreadViewModel)
}