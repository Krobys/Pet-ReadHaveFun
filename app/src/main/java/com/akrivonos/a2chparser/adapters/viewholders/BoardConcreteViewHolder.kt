package com.akrivonos.a2chparser.adapters.viewholders

import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.database.RoomAppDatabase
import com.akrivonos.a2chparser.databinding.AdapteritemConcreteBoardBinding
import com.akrivonos.a2chparser.interfaces.OpenBoardListener
import com.akrivonos.a2chparser.models.database.Board
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BoardConcreteViewHolder(var binding: AdapteritemConcreteBoardBinding, openBoardListener: OpenBoardListener, private val database: RoomAppDatabase) : RecyclerView.ViewHolder(binding.root) {

    private val imageButtonSave: ImageButton = itemView.findViewById(R.id.button_is_save)

    init {
        itemView.setOnClickListener {
            openBoardListener.openBoard(binding.board)
        }
        imageButtonSave.setOnClickListener {
            (if (binding.isSaveState)
                database.boardsDao().deleteBoard(binding.board?.idBoard)
            else
                database.boardsDao().saveBoard(binding.board)
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe()
        }
    }

    fun bind(board: Board): Disposable {
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
