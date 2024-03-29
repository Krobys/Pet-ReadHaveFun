package com.akrivonos.a2chparser.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.database.BoardsDao
import com.akrivonos.a2chparser.databinding.AdapteritemConcreteBoardBinding
import com.akrivonos.a2chparser.interfaces.OpenBoardListener
import com.akrivonos.a2chparser.models.database.Board
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BoardConcreteViewHolder(var binding: AdapteritemConcreteBoardBinding, private var openBoardListener: OpenBoardListener, private val boardsDao: BoardsDao) : RecyclerView.ViewHolder(binding.root) {

    fun openBoard() {
        openBoardListener.openBoard(binding.board)
    }

    fun changeStateBoard() {
        (if (binding.isSaveState)
            boardsDao.deleteBoard(binding.board?.idBoard)
        else
            boardsDao.saveBoard(binding.board))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe()
    }

    fun bind(board: Board): Disposable {
        binding.holder = this
        binding.board = board
        return boardsDao.getBoardById(board.idBoard)
                .subscribeOn(Schedulers.io())
                .map { list -> list.isNotEmpty() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isSaveLocal ->
                    binding.isSaveState = isSaveLocal
                    binding.executePendingBindings()
                }
    }
}
