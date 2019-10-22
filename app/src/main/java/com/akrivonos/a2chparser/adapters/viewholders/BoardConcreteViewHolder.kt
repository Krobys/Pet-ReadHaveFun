package com.akrivonos.a2chparser.adapters.viewholders

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.database.RoomAppDatabase
import com.akrivonos.a2chparser.interfaces.OpenBoardListener
import com.akrivonos.a2chparser.models.database.Board
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BoardConcreteViewHolder(itemView: View, openBoardListener: OpenBoardListener, private val database: RoomAppDatabase) : RecyclerView.ViewHolder(itemView) {

    private val nameBoardTextView: TextView = itemView.findViewById(R.id.name_board)
    private val idBoardTextView: TextView = itemView.findViewById(R.id.id_board)
    private val imageButtonSave: ImageButton = itemView.findViewById(R.id.button_is_save)
    private lateinit var board: Board
    private var isSave: Boolean = false

    init {
        itemView.setOnClickListener {
            openBoardListener.openBoard(board)

        }
        imageButtonSave.setOnClickListener {
            Log.d("test", "onclick:")
            (if (isSave)
                database.boardsDao().deleteBoard(board.idBoard)
            else
                database.boardsDao().saveBoard(board)
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe()
        }
    }

    fun setBoard(board: Board): Disposable {
        this.board = board
        nameBoardTextView.text = board.nameBoards
        val idBoard = "/${board.idBoard}/"
        idBoardTextView.text = idBoard
        return database.boardsDao().getBoardById(board.idBoard)
                .subscribeOn(Schedulers.io())
                .map { list -> list.isNotEmpty() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isSaveLocal ->
                    Log.d("test", "isSaveLocal: $isSaveLocal id: ${board.idBoard}")
                    isSave = isSaveLocal
                    imageButtonSave.setBackgroundResource(if (isSaveLocal)
                        R.drawable.ic_star_save
                    else
                        R.drawable.ic_star_not_save)
                }
    }
}
