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
import com.akrivonos.a2chparser.viewholders.BoardConcreteViewHolder

class SaveConcreteBoardsAdapter(context: Context?, private val openBoardListener: OpenBoardListener, private val roomAppDatabase: RoomAppDatabase) : RecyclerView.Adapter<BoardConcreteViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)
    private var boardsList: ArrayList<Board> = ArrayList()

    fun setBoardsList(boardsList: List<Board>) {
        this.boardsList = ArrayList(boardsList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardConcreteViewHolder {
        val binding: AdapteritemConcreteBoardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.adapteritem_concrete_board, parent, false)
        return BoardConcreteViewHolder(binding, openBoardListener, roomAppDatabase.boardsDao())
    }

    override fun getItemCount(): Int = boardsList.size


    override fun onBindViewHolder(holder: BoardConcreteViewHolder, position: Int) {
    }
}