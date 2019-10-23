package com.akrivonos.a2chparser.adapters.viewholders

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.database.RoomAppDatabase
import com.akrivonos.a2chparser.databinding.AdapteritemConcreteBoardBinding
import com.akrivonos.a2chparser.interfaces.OpenBoardListener
import com.akrivonos.a2chparser.models.database.Board
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BoardConcreteViewHolder(var binding: AdapteritemConcreteBoardBinding, private var openBoardListener: OpenBoardListener, private val database: RoomAppDatabase) : RecyclerView.ViewHolder(binding.root) {

    fun openBoard() {
        openBoardListener.openBoard(binding.board)
    }

    fun changeStateBoard() {
        (if (binding.isSaveState)
            database.boardsDao().deleteBoard(binding.board?.idBoard)
        else
            database.boardsDao().saveBoard(binding.board))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe()
    }

    fun bind(board: Board): Disposable {
        binding.holder = this
        binding.board = board
        return database.boardsDao().getBoardById(board.idBoard)
                .subscribeOn(Schedulers.io())
                .map { list -> list.isNotEmpty() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isSaveLocal ->
                    Log.d("test", "isSaveLocal: $isSaveLocal id: ${board.idBoard}")
                    binding.isSaveState = isSaveLocal
                    binding.executePendingBindings()
                }
    }
}
