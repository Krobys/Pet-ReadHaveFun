package com.akrivonos.a2chparser.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.viewholders.BoardConcreteViewHolder
import com.akrivonos.a2chparser.database.RoomAppDatabase
import com.akrivonos.a2chparser.databinding.AdapteritemConcreteBoardBinding
import com.akrivonos.a2chparser.interfaces.OpenBoardListener
import com.akrivonos.a2chparser.models.database.Board
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardConcrete
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
        Log.d("test", "disposeall:")
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

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        Log.d("test", "onDetachedFromRecyclerView: ")
    }

    override fun onViewDetachedFromWindow(holder: BoardConcreteViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Log.d("test", "onViewDetachedFromWindow: ")
    }

    override fun getItemCount(): Int {
        return boards.size
    }
}
