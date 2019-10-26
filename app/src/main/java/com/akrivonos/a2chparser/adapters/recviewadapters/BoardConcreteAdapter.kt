package com.akrivonos.a2chparser.adapters.recviewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.database.RoomAppDatabase
import com.akrivonos.a2chparser.databinding.AdapteritemConcreteBoardBinding
import com.akrivonos.a2chparser.interfaces.OpenBoardListener
import com.akrivonos.a2chparser.models.database.Board
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardConcrete
import com.akrivonos.a2chparser.viewholders.BoardConcreteViewHolder
import io.reactivex.disposables.Disposable

class BoardConcreteAdapter(context: Context, private val openBoardListener: OpenBoardListener) : RecyclerView.Adapter<BoardConcreteViewHolder>() {

    private var boards = ArrayList<Board>()
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val appDatabase: RoomAppDatabase? = RoomAppDatabase.getAppDataBase(context)
    private val arrayDisposed: ArrayList<Disposable> = ArrayList()

    fun setBoardConcretes(boardConcretes: List<BoardConcrete>) {
        boards.clear()
        for (boardConcrete in boardConcretes) {
            boards.add(Board(boardConcrete))
        }
    }

    fun setBoards(boards: List<Board>) {
        this.boards = ArrayList(boards)
    }

    fun disposeAll() {
        for (disposable in arrayDisposed) {
            disposable.dispose()
        }
        arrayDisposed.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardConcreteViewHolder {
        val binding = DataBindingUtil.inflate<AdapteritemConcreteBoardBinding>(layoutInflater, R.layout.adapteritem_concrete_board, parent, false)
        return BoardConcreteViewHolder(binding, openBoardListener, appDatabase!!)
    }

    override fun onBindViewHolder(holder: BoardConcreteViewHolder, position: Int) {
        val board = boards[position]
        arrayDisposed.add(holder.bind(board))
    }

    override fun getItemCount(): Int {
        return boards.size
    }
}
