package com.akrivonos.a2chparser.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akrivonos.a2chparser.viewmodels.BoardsViewModel
import com.akrivonos.a2chparser.viewmodels.ConcreteBoardViewModel
import com.akrivonos.a2chparser.viewmodels.ConcreteThreadViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(BoardsViewModel::class)
    abstract fun bindBoardsViewModel(boardsViewModel: BoardsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConcreteBoardViewModel::class)
    abstract fun bindConcreteBoardViewModel(concreteBoardViewModel: ConcreteBoardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConcreteThreadViewModel::class)
    abstract fun bindConcreteThreadViewModel(concreteThreadViewModel: ConcreteThreadViewModel): ViewModel
}
